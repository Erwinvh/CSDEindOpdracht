package com.example.csdeindopdracht.Logic;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.csdeindopdracht.Database.Entity.Statistic;
import com.example.csdeindopdracht.Database.Relations.RunnerStatistics;
import com.example.csdeindopdracht.Database.Relations.TrainingStatistics;
import com.example.csdeindopdracht.data.GpsLocation;
import com.example.csdeindopdracht.data.Runner;
import com.example.csdeindopdracht.data.Training;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TrainingLogic {

    private final String TAG = this.getClass().getSimpleName();

    private final int MAX_STAMINA = 3600; // 1 Hours in seconds.
    private final double MAX_STAMINA_PERCENTAGE = (double) MAX_STAMINA / 100;

    private final int MAX_SPEED = 25; // average km/h
    private final double MAX_SPEED_PERCENTAGE = (double) MAX_SPEED / 100;

    private final int MAX_TOP_SPEED = 40; // km/h at Usain Bolt speed
    private final double MAX_TOP_SPEED_PERCENTAGE = (double) MAX_TOP_SPEED / 100;

    private MainViewModel mainViewModel;

    private String username;
    private int time;

    private Training training = null;
    private Timer trainingTimer;

    public boolean startNewTraining(MainViewModel mainViewModel) {
        if (this.training != null && !this.training.isFinalised()) {
            Log.e(TAG, "Another t" + "training is still in progress.");
            return false;
        }

        this.mainViewModel = mainViewModel;

        this.username = "";
        this.time = 0;
        this.training = new Training(mainViewModel.getApplication().getApplicationContext());

        this.trainingTimer = new Timer("Training timer");
        this.trainingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                training.updateLocation();
            }
        }, 0, 1000);
        return true;
    }

    public void stopCurrentTraining(Context context, LifecycleOwner owner) {
        // Get necessary information
        Runner runner = calculateRunner(context, owner);
        com.example.csdeindopdracht.Database.Entity.Training training = getLastTraining(context, owner);

        // Save statistics to database.
        final com.example.csdeindopdracht.Database.Entity.Runner[] player = new com.example.csdeindopdracht.Database.Entity.Runner[1];
        final Statistic[] statistic = new Statistic[1];

        mainViewModel.getPlayer().observe(owner, runnerStatistics -> {
            Log.d(TAG, "lambda test.");
            player[0] = runnerStatistics.getRunner();
            statistic[0] = runnerStatistics.getStatistic();

            int id;
            if (training != null) {
                id = statistic[0].getId();
            } else {
                id = 0;
            }

            mainViewModel.saveRunner(new RunnerStatistics(
                    new com.example.csdeindopdracht.Database.Entity.Runner(
                            player[0].getName(),
                            player[0].getPhotoPath(),
                            player[0].isPlayer(),
                            player[0].isComplete(),
                            player[0].getStatisticsID()),
                    new Statistic(id,
                            (double) runner.topSpeed,
                            (double) runner.getSpeed(),
                            (double) runner.getStamina()
                    )));
        });

        this.training = null;
        this.trainingTimer.cancel();
        this.trainingTimer.purge();
        this.trainingTimer = null;
    }

    private Runner calculateRunner(Context context, LifecycleOwner owner) {
        ArrayList<GpsLocation> route = this.training.getRoute();

        // Remove elements before the first GPS change update.
        route.removeIf(item -> item.getLocation() == null);

        // TODO get previous statistics.
        if (route.size() <= 1) {
            return new Runner("Player", 10, 10, 10);
        }

        // Calculate average speed AND top speed.
        int totalDistance = 0;
        int totalTime = 0;
        int topSpeed = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            int distance = (int) (route.get(i).getLocation().distanceToAsDouble(route.get(i + 1).getLocation()) * 100);
            int time = (int) ((route.get(i + 1).getTimestamp() - route.get(i).getTimestamp()));

            // Top speed.
            int speed = (int) (((double) distance / 100 / 1000) / ((double) time / 3600 / 1000) / MAX_SPEED_PERCENTAGE);
            if (topSpeed < speed) {
                topSpeed = (int) Math.min(speed / MAX_TOP_SPEED_PERCENTAGE, MAX_TOP_SPEED);
            }

            // Accumulating variables.
            totalDistance += distance;
            totalTime += time;
        }
        // Get player name.
        Repository.getInstance().getPlayer(context).observe(owner, runner -> {
            this.username = runner.getName();
        });

        // Initialise time.
        this.time = totalTime / 1000;

        // Calculate stamina.
        int stamina = (int) Math.min(
                totalTime / 1000 / MAX_STAMINA_PERCENTAGE,
                (int) (MAX_STAMINA / MAX_STAMINA_PERCENTAGE)
        );

        // Calculate speed.
        int speed = Math.min(
                (int) (((double) totalDistance / 100 / 1000) / ((double) totalTime / 1000 / 3600) / MAX_SPEED_PERCENTAGE),
                (int) (MAX_SPEED / MAX_SPEED_PERCENTAGE)
        );

        Runner runner = new Runner(username, stamina, speed, topSpeed);
        this.training.finaliseTraining(runner);
        Log.d(TAG, "Runner statistics: " + runner.toString());
        return runner;
    }

    public com.example.csdeindopdracht.Database.Entity.Training getLastTraining(Context context, LifecycleOwner owner) {
        final com.example.csdeindopdracht.Database.Entity.Training[] training = new com.example.csdeindopdracht.Database.Entity.Training[1];

        LiveData<List<TrainingStatistics>> trainings = Repository.getInstance().getTrainings(context);
        trainings.observe(owner, trainingStatistics -> {
            try {
                training[0] = trainingStatistics.get(trainingStatistics.size() - 1).getTraining();
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "No trainings available: " + e.getLocalizedMessage());
                training[0] = null;
            }
        });

        return training[0];
    }
}
