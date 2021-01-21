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
import com.example.csdeindopdracht.Database.Entity.Runner;
import com.example.csdeindopdracht.Database.Entity.Statistic;
import com.example.csdeindopdracht.Database.Entity.UserSettings;
import com.example.csdeindopdracht.Database.Relations.RaceWithRunners;
import com.example.csdeindopdracht.Database.Relations.RunnerStatistics;
import com.example.csdeindopdracht.Database.Relations.TrainingStatistics;
import com.example.csdeindopdracht.MainActivity;

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
    private final MutableLiveData<TrainingStatistics> Training = new MutableLiveData<>();
    public MainActivity activity;
    //private final MutableLiveData<GpsCoordinate> gpsCoordinate = new MutableLiveData<>();


    private final MutableLiveData<RaceWithRunners> lastUncompletedRace = new MutableLiveData<>();
    private final MutableLiveData<TrainingStatistics> lastTraining = new MutableLiveData<>();

    public RaceLogic raceLogic;
    private TrainingLogic trainingLogic;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }
    public void startTraining() {
        if (trainingLogic == null) {
            trainingLogic = new TrainingLogic();
        }
        trainingLogic.startNewTraining(this);
    }


    public void stopTraining(LifecycleOwner owner) {
        if (trainingLogic == null) {
            trainingLogic = new TrainingLogic();
        }
        trainingLogic.stopCurrentTraining(getApplication().getApplicationContext(), owner);
    }

    public RaceLogic getRaceLogic(){
        if (raceLogic == null){
            raceLogic = new RaceLogic(getApplication().getApplicationContext(), this);
        }
        return raceLogic;
    }

    public void setMainActivity(MainActivity activity){
        this.activity = activity;
    }
    public LiveData<UserSettings> getUserSetting() {
        updateUserSetting();
        return userSettings;
    }

    public String getCurrentOpponentImage() {
        //TODO: get opponentImage
        return "opponent1";
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

    public LiveData<RunnerStatistics> getRunnerStatistics(String runnerName) {
        return Repository.getInstance().getStatistics(getApplication().getApplicationContext(), runnerName);
    }

    public void saveTrainingStatistics(TrainingStatistics trainingStatistics) {
        Repository.getInstance().updateTraining(getApplication().getApplicationContext(), trainingStatistics);
    }

    public void saveRunner(RunnerStatistics runnerStatistics) {
        Repository.getInstance().updateRunnerStatistics(getApplication().getApplicationContext(), runnerStatistics);
    }

    public double getMaxSpeed() {
        //TODO: get player maxspeed
        return 3;
    }

    public double getSpeed() {
        //TODO: get player speed
        return 1;
    }

    public double getOpponentMaxSpeed() {
        //TODO: get oppo max speed
        return 2;
    }

    public double getOpponentSpeed() {
        //TODO: get oppo speed
        return 1;
    }

    public int getStamina() {
        //TODO: get player stamina

        return 4;
    }

    public int getOpponentStamina() {
        //TODO: get opponent Stamina
        return 3;
    }

    public void completeRace() {
        //TODO: complete last unrun race or just leave it as is
    }

    public void setLanguage(LifecycleOwner owner, String language) {
        this.userSettings.observe(owner, settings -> {
        updateUserSetting();
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

    public LiveData<RunnerStatistics> getPlayer() {
        return Repository.getInstance().getStatistics(getApplication().getApplicationContext(), "Player");
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
