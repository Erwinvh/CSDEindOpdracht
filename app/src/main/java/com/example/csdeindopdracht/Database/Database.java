package com.example.csdeindopdracht.Database;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.csdeindopdracht.Database.Entity.Race;
import com.example.csdeindopdracht.Database.Entity.RaceRegister;
import com.example.csdeindopdracht.Database.Entity.Runner;
import com.example.csdeindopdracht.Database.Entity.Statistic;
import com.example.csdeindopdracht.Database.Entity.Training;
import com.example.csdeindopdracht.Database.Entity.UserSettings;

import static android.content.ContentValues.TAG;

@androidx.room.Database(entities = {Race.class, RaceRegister.class, Runner.class, Statistic.class, Training.class, UserSettings.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    private volatile static Database INSTANCE;

    public abstract UserDao userAccess();
    public abstract AdminDao adminAccess();

    public static Database getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "db1.db")
                                .fallbackToDestructiveMigration()
                                .createFromAsset("RunForestV1.4.db")
                                .build();
                    } else {
                        Log.e(TAG, "NO PERMISSION TO ACCESS DATABASE");
                    }
                }
            }
        }
        return INSTANCE;
    }
}
