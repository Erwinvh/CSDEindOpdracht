package com.example.csdeindopdracht.Logic;

import android.content.Context;
import android.util.Log;

import com.example.csdeindopdracht.data.GpsLocation;
import com.example.csdeindopdracht.data.Runner;
import com.example.csdeindopdracht.data.Training;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TrainingLogic {

    private final String TAG = this.getClass().getSimpleName();

    private final int MAX_STAMINA = 7200; // 2 Hours in seconds.
    private final double MAX_STAMINA_PERCENTAGE = MAX_STAMINA / 100;

    private final int MAX_SPEED = 16; // average km/h
    private final double MAX_SPEED_PERCENTAGE = MAX_SPEED / 100;

    private final int MAX_TOP_SPEED = 45; // km/h at Usain Bolt top speed
    private final double MAX_TOP_SPEED_PERCENTAGE = MAX_TOP_SPEED / 100;

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

    public void stopCurrentTraining() {
        Runner runner = calculateRunner();

        // TODO save runner and statistics to database.

        this.training = null;
        this.trainingTimer.cancel();
        this.trainingTimer = null;
    }

    private Runner calculateRunner() {
        ArrayList<GpsLocation> route = this.training.getRoute();

        // Remove elements before the first GPS change update.
        route.removeIf(item -> item.getLocation() == null);


        // Calculate average speed AND top speed.
        int totalDistance = 0;
        int totalTime = 0;
        int topSpeed = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            int distance = (int) (route.get(i).getLocation().distanceToAsDouble(route.get(i+1).getLocation()) * 100);
            int time = (int) ((route.get(i+1).getTimestamp() - route.get(i).getTimestamp()) / 1000);

            // Top speed.
            int speed = (int) ((distance / 100000) * (time / 3600) / MAX_SPEED_PERCENTAGE);
            if (topSpeed < speed) {
                topSpeed = (int) Math.min(speed / MAX_TOP_SPEED_PERCENTAGE, MAX_TOP_SPEED);
            }

            // Accumulating variables.
            totalDistance += distance;
            totalTime += time;
        }
        // Get player name.
        String name = ""; // TODO get runner name from database

        // Calculate stamina.
        int stamina = (int) Math.min(
                totalTime / MAX_STAMINA_PERCENTAGE,
                MAX_STAMINA / MAX_STAMINA_PERCENTAGE
        );

        // Calculate speed.
        int speed = Math.min(
                (int) ((totalDistance / 100000) * (totalTime / 3600) / MAX_SPEED_PERCENTAGE),
                MAX_SPEED
        );

        Runner runner = new Runner(name, stamina, speed, topSpeed);
        Log.d(TAG, "Runner statistics: " + runner.toString());
        return runner;
    }
}
