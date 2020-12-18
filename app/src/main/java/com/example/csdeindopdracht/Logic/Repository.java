package com.example.csdeindopdracht.Logic;

import android.content.Context;

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

    public List<Training> getTrainings(Context context){
        //TODO: finish
        return null;
    }

    public UserSetting getUserSetting(Context context){
        //TODO: finish
        return null;
    }

    public Race getRace(Context context){
        //TODO: finish
        return null;
    }

    public Training getLastTraining(Context context){
        //TODO: finish
        return null;
    }

    public void setLanguage(Context context, String language){
        //TODO: finish
    }

    public void completeRace(Context context, Race race){
        //TODO: finish
    }

    public void setDifficulty(Context context, int difficulty){
        //TODO: finish
    }

    public void addTraining(Context context, Training training){
        //TODO: finish
    }

    public void updateTraining(Context context, Training training){
        //TODO: finish
    }

    public int getDifficulty(Context context){
        //TODO: finish
        return 0;
    }
}
