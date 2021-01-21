package com.example.csdeindopdracht.Logic;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.csdeindopdracht.Database.Entity.UserSettings;
import com.example.csdeindopdracht.Database.Relations.TrainingStatistics;
import com.example.csdeindopdracht.MainActivity;

import java.util.Locale;

/**
 * ViewModel for the Main Activity.
 *
 * Manages all Live Data and serves as a connector class between the front and backend.
 */
public class MainViewModel extends AndroidViewModel {

    private static final Locale LOCALE_DEFAULT = new Locale("nl");
    private final MutableLiveData<UserSettings> userSettings = new MutableLiveData<>();
    private final MutableLiveData<TrainingStatistics> Training = new MutableLiveData<>();
    public MainActivity activity;
    //private final MutableLiveData<GpsCoordinate> gpsCoordinate = new MutableLiveData<>();

    public RaceLogic raceLogic;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void setMainActivity(MainActivity activity){
        this.activity = activity;
    }

    public MutableLiveData<UserSettings> getUserSetting() {
        this.userSettings.postValue(Repository.getInstance().getUserSetting(getApplication().getApplicationContext()));
        return userSettings;
    }

    public RaceLogic getRaceLogic(){
        if (raceLogic == null){
            raceLogic = new RaceLogic(getApplication().getApplicationContext(), this);
        }
        return raceLogic;
    }

    public double getMaxSpeed() {
        //TODO: get player maxspeed
        return 3;
    }

    public double getSpeed() {
        //TODO: get player speed
        return 2;
    }

    public double getOpponentMaxSpeed() {
        //TODO: get oppo max speed
        return 0;
    }

    public double getOpponentSpeed() {
        //TODO: get oppo speed
        return 1;
    }

    public int getStamina() {
        //TODO: get player stamina

        return 100;
    }

    public int getOpponentStamina() {
        //TODO: get opponent Stamina
        return 0;
    }

    public void completeRace() {
        //TODO: complete last unrun race or just leave it as is
    }

    public String getCurrentOpponentImage() {
        //TODO: get opponentImage
        return "opponent1";
    }

//    public MutableLiveData<TrainingStatistics> getTraining(){
//        this.Training.postValue(Repository.getInstance().);
//    }

//    public MutableLiveData<GpsCoordinate> getGpsCoordinate() {
//        this.gpsCoordinate.postValue(Repository.getInstance().getGpsCoordinate());
//        return gpsCoordinate;
//    }

}
