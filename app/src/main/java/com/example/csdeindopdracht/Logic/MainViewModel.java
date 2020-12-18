package com.example.csdeindopdracht.Logic;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.csdeindopdracht.Database.Entity.UserSettings;
import com.example.csdeindopdracht.Database.Relations.TrainingStatistics;

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
    //private final MutableLiveData<GpsCoordinate> gpsCoordinate = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<UserSettings> getUserSetting() {
        this.userSettings.postValue(Repository.getInstance().getUserSetting(getApplication().getApplicationContext()));
        return userSettings;
    }

//    public MutableLiveData<TrainingStatistics> getTraining(){
//        this.Training.postValue(Repository.getInstance().);
//    }

//    public MutableLiveData<GpsCoordinate> getGpsCoordinate() {
//        this.gpsCoordinate.postValue(Repository.getInstance().getGpsCoordinate());
//        return gpsCoordinate;
//    }

}
