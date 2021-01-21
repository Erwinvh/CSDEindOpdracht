package com.example.csdeindopdracht.Logic;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;

import com.example.csdeindopdracht.Database.Entity.Statistic;
import com.example.csdeindopdracht.Database.Relations.TrainingStatistics;
import com.example.csdeindopdracht.data.GpsLocation;
import com.example.csdeindopdracht.data.Runner;
import com.example.csdeindopdracht.data.Training;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TrainingLogic {

    private final String TAG = this.getClass().getSimpleName();

    private String username = "";

    private final int MAX_STAMINA = 600; // 2 Hours in seconds. // TODO test purpose: 10 minutes
    private final double MAX_STAMINA_PERCENTAGE = (double) MAX_STAMINA / 100;

    private final int MAX_SPEED = 16; // average km/h
    private final double MAX_SPEED_PERCENTAGE = (double) MAX_SPEED / 100;

    private final int MAX_TOP_SPEED = 45; // km/h at Usain Bolt top speed
    private final double MAX_TOP_SPEED_PERCENTAGE = (double) MAX_TOP_SPEED / 100;

    private Training training = null;
    private Timer trainingTimer = new Timer("Training timer");

    public boolean startNewTraining(Context context) {
        if (this.training != null && !this.training.isFinalised()) {
            Log.e(TAG, "Another training is still in progress.");
            return false;
        }

        this.training = new Training(context);
        this.trainingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                training.updateLocation();
            }
        }, 0, 1000);
        return true;
    }

    public void stopCurrentTraining(Context context, LifecycleOwner owner) {
        Runner runner = calculateRunner(context, owner);
//        Repository.getInstance().addTraining(
//                context,
//                new TrainingStatistics(
//                        new com.example.csdeindopdracht.Database.Entity.Training(training.getDate().toString(), "0", 1),
//                        new Statistic()
//        ));

        this.training = null;
        this.trainingTimer.cancel();
        this.trainingTimer.purge();
    }

    private Runner calculateRunner(Context context, LifecycleOwner owner) {
        ArrayList<GpsLocation> route = this.training.getRoute();

        // Remove elements before the first GPS change update.
        route.removeIf(item -> item.getLocation() == null);


        // Calculate average speed AND top speed.
        int totalDistance = 0;
        int totalTime = 0;
        int topSpeed = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            int distance = (int) (route.get(i).getLocation().distanceToAsDouble(route.get(i+1).getLocation()) * 100);
            int time = (int) ((route.get(i+1).getTimestamp() - route.get(i).getTimestamp()));

            // Top speed.
            int speed = (int) (((double) distance / 100 / 1000) / ((double)time / 3600 / 1000) / MAX_SPEED_PERCENTAGE);
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
        Log.d(TAG, "Runner statistics: " + runner.toString());
        return runner;
    }
}
