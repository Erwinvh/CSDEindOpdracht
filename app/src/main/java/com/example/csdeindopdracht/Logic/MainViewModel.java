package com.example.csdeindopdracht.Logic;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Locale;

/**
 * ViewModel for the Main Activity.
 *
 * Manages all Live Data and serves as a connector class between the front and backend.
 */
public class MainViewModel {

    private static final Locale LOCALE_DEFAULT = new Locale("nl");
    private final MutableLiveData<UserSettings> userSettings = new MutableLiveData<>();
    private final MutableLiveData<GpsCoordinate> gpsCoordinate = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<UserSettings> getUserSettings() {
        this.userSettings.postValue(Repository.getInstance().getUserSettings(getApplication().getApplicationContext()));
        return userSettings;
    }

    public MutableLiveData<GpsCoordinate> getGpsCoordinate() {
        this.gpsCoordinate.postValue(Repository.getInstance().getGpsCoordinate());
        return gpsCoordinate;
    }

}
