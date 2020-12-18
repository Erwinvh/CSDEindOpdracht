package com.example.csdeindopdracht.Logic;

import android.content.Context;

import com.example.csdeindopdracht.Database.Database;
import com.example.csdeindopdracht.Database.Entity.Race;
import com.example.csdeindopdracht.Database.Entity.Training;
import com.example.csdeindopdracht.Database.Entity.UserSettings;
import com.example.csdeindopdracht.Database.Relations.RaceWithRunners;
import com.example.csdeindopdracht.Database.Relations.TrainingStatistics;

import java.util.List;

/**
 * Repository is a collection class which converges all backend data streams.
 * This Repository serves as a connection to the DataBase and the GPS Manager.
 */
public class Repository {

    private static volatile Repository INSTANCE;
    private final String UserSettingsID = "0";

    public static Repository getInstance() {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                INSTANCE = new Repository();
            }
        }
        return INSTANCE;
    }

    public List<TrainingStatistics> getTrainings(Context context){
        return Database.getINSTANCE(context).userAccess().getTrainingStatistics();
    }

    public UserSettings getUserSetting(Context context){
        return Database.getINSTANCE(context).userAccess().getUserSettings(this.UserSettingsID);
        //TODO: What settingsID? shouldnt that be a standard return?
    }

    public RaceWithRunners getRace(Context context, String id){
        return Database.getINSTANCE(context).userAccess().getRaceRunners(id);
    }

    public RaceWithRunners getLastUncompletedRace(Context context){
        List<RaceWithRunners> races = Database.getINSTANCE(context).userAccess().getRacesWithRunners();
        for (RaceWithRunners race : races) {
            if (!race.getRace().isComplete()){
                return race;
            }
        }
        //This is so there is always a race being sent. replay value?
        return races.get(races.size()-1);
    }


    public List<RaceWithRunners> getRaces(Context context){
        return Database.getINSTANCE(context).userAccess().getRacesWithRunners();
    }

    public TrainingStatistics getLastTraining(Context context){
        List<TrainingStatistics> trainings = getTrainings(context);
        return trainings.get(trainings.size()-1);
    }

    public void setLanguage(Context context, String language){
        //TODO: check this?
        UserSettings userSettings = Database.getINSTANCE(context).userAccess().getUserSettings(this.UserSettingsID);
        userSettings.setLanguage(language);
        Database.getINSTANCE(context).adminAccess().updateUserSettings(userSettings);
    }

    public void completeRace(Context context, Race race){
        //TODO: check this?
        race.setComplete(true);
        Database.getINSTANCE(context).adminAccess().updateRaces(race);
    }

    public void setDifficulty(Context context, int difficulty){
        //TODO: check this?
        UserSettings userSettings = Database.getINSTANCE(context).userAccess().getUserSettings(this.UserSettingsID);
        userSettings.setDifficulty(difficulty);
        Database.getINSTANCE(context).adminAccess().updateUserSettings(userSettings);
    }

    public void addTraining(Context context, TrainingStatistics training){
        List<TrainingStatistics> trainings = Database.getINSTANCE(context).userAccess().getTrainingStatistics();
        trainings.add(training);
        Database.getINSTANCE(context).adminAccess().addTrainings(training.getTraining());
        Database.getINSTANCE(context).adminAccess().addStatistics(training.getStatistic());
    }

    public void updateTraining(Context context, TrainingStatistics training){
        Database.getINSTANCE(context).adminAccess().updateTrainings(training.getTraining());
        Database.getINSTANCE(context).adminAccess().updateStatistics(training.getStatistic());

    }

    public int getDifficulty(Context context){
        return Database.getINSTANCE(context).userAccess().getUserSettings(this.UserSettingsID).getDifficulty();
    }
}
