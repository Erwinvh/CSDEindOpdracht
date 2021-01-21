package com.example.csdeindopdracht.Logic;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.csdeindopdracht.Database.Database;
import com.example.csdeindopdracht.Database.Entity.Race;
import com.example.csdeindopdracht.Database.Entity.Runner;
import com.example.csdeindopdracht.Database.Entity.Statistic;
import com.example.csdeindopdracht.Database.Entity.Training;
import com.example.csdeindopdracht.Database.Entity.UserSettings;
import com.example.csdeindopdracht.Database.Relations.RaceWithRunners;
import com.example.csdeindopdracht.Database.Relations.RunnerStatistics;
import com.example.csdeindopdracht.Database.Relations.TrainingStatistics;

import java.util.List;

/**
 * Repository is a collection class which converges all backend data streams.
 * This Repository serves as a connection to the DataBase and the GPS Manager.
 */
public class Repository {

    private static volatile Repository INSTANCE;

    public static Repository getInstance() {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                INSTANCE = new Repository();
            }
        }
        return INSTANCE;
    }

    public LiveData<List<TrainingStatistics>> getTrainings(Context context){
        return Database.getINSTANCE(context).userAccess().getTrainingStatistics();
    }

    public LiveData<UserSettings> getUserSetting(Context context){
        return Database.getINSTANCE(context).userAccess().getUserSettings();
    }

    public LiveData<RaceWithRunners> getRace(Context context, String id){
        return Database.getINSTANCE(context).userAccess().getRaceRunners(id);
    }

    public LiveData<List<RaceWithRunners>> getRaces(Context context){
        return Database.getINSTANCE(context).userAccess().getRacesWithRunners();
    }

    public LiveData<RunnerStatistics> getStatistics(Context context, String name) {
        return Database.getINSTANCE(context).userAccess().getRunnerStatistics(name);
    }
    public void updateUserSettings(Context context, UserSettings userSettings){
        Thread updateThread = new Thread(() -> Database.getINSTANCE(context).adminAccess().updateUserSettings(userSettings));
        updateThread.start();
        try {
            updateThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateRace(Context context, Race race){
        Thread updateThread = new Thread(() -> Database.getINSTANCE(context).adminAccess().updateRaces(race));
        updateThread.start();
        try {
            updateThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addTraining(Context context, TrainingStatistics training){
        Thread updateThread = new Thread(() -> {
            Database.getINSTANCE(context).adminAccess().addStatistics(training.getStatistic());
            Database.getINSTANCE(context).adminAccess().addTrainings(training.getTraining());
        });
        updateThread.start();
        try {
            updateThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateTraining(Context context, TrainingStatistics training){
        Thread updateThread = new Thread(() -> {
            Database.getINSTANCE(context).adminAccess().updateStatistics(training.getStatistic());
            Database.getINSTANCE(context).adminAccess().updateTrainings(training.getTraining());
        });
        updateThread.start();
        try {
            updateThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateRunnerStatistics(Context context, RunnerStatistics runnerStatistics) {
        Thread updateThread = new Thread(() -> {
            Database.getINSTANCE(context).adminAccess().updateStatistics(runnerStatistics.getStatistic());
            Database.getINSTANCE(context).adminAccess().updateRunners(runnerStatistics.getRunner());
        });
        updateThread.start();
        try {
            updateThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
