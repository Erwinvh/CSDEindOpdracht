package com.example.csdeindopdracht.Logic;


import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.csdeindopdracht.Database.Database;
import com.example.csdeindopdracht.Database.Entity.Race;
import com.example.csdeindopdracht.Database.Entity.UserSettings;
import com.example.csdeindopdracht.Database.Relations.RaceWithRunners;
import com.example.csdeindopdracht.Database.Relations.TrainingStatistics;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

/**
 * ViewModel for the Main Activity.
 *
 * Manages all Live Data and serves as a connector class between the front and backend.
 */
public class MainViewModel extends AndroidViewModel {

    private static final Locale LOCALE_DEFAULT = new Locale("nl");
    private LiveData<UserSettings> userSettings = new MutableLiveData<>();
    private LiveData<TrainingStatistics> Training = new MutableLiveData<>();
    //private final MutableLiveData<GpsCoordinate> gpsCoordinate = new MutableLiveData<>();

    private final MutableLiveData<RaceWithRunners> lastUncompletedRace = new MutableLiveData<>();
    private final MutableLiveData<TrainingStatistics> lastTraining = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<UserSettings> getUserSetting() {
        updateUserSetting();
        return userSettings;
    }

    public LiveData<RaceWithRunners> getLastUncompletedRace(LifecycleOwner owner){
        LiveData<List<RaceWithRunners>> races = Repository.getInstance().getRaces(getApplication().getApplicationContext());
        races.observe(owner, raceWithRunners -> {
            for (RaceWithRunners race : raceWithRunners) {
                if (!race.getRace().isComplete()){
                    this.lastUncompletedRace.setValue(race);
                }
            }
        });
        return lastUncompletedRace;
    }

    public LiveData<TrainingStatistics> getLastTraining(LifecycleOwner owner){
        LiveData<List<TrainingStatistics>> races = Repository.getInstance().getTrainings(getApplication().getApplicationContext());
        races.observe(owner, trainingStatistics -> {
            this.lastTraining.setValue(trainingStatistics.get(trainingStatistics.size()-1));
        });
        return lastTraining;
    }

    public void setLanguage(LifecycleOwner owner, String language) {
        updateUserSetting();
        this.userSettings.observe(owner, settings -> {
            settings.setLanguage(language);
            Repository.getInstance().updateUserSettings(getApplication().getApplicationContext(), settings);
        });
    }

    public void completeRace(Context context, Race race){
        race.setComplete(true);
        Repository.getInstance().updateRace(getApplication().getApplicationContext(), race);
    }

    public void setDifficulty(LifecycleOwner owner, int difficulty){
        updateUserSetting();
        this.userSettings.observe(owner, settings -> {
            settings.setDifficulty(difficulty);
            Repository.getInstance().updateUserSettings(getApplication().getApplicationContext(), settings);
        });
    }

    private void updateUserSetting() {
        this.userSettings = Repository.getInstance().getUserSetting(getApplication().getApplicationContext());
    }

    //todo This is most likely not possible to place here. What will most likely needs to be done if for the ui/logic which needs it to get the settings and pull the difficulty itself.
//    public int getDifficulty(){
//        return Database.getINSTANCE(context).userAccess().getUserSettings(this.UserSettingsID).getDifficulty();
//    }

//    public MutableLiveData<TrainingStatistics> getTraining(){
//        this.Training.postValue(Repository.getInstance().);
//    }

//    public MutableLiveData<GpsCoordinate> getGpsCoordinate() {
//        this.gpsCoordinate.postValue(Repository.getInstance().getGpsCoordinate());
//        return gpsCoordinate;
//    }

}
