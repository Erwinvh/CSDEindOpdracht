package com.example.csdeindopdracht.Database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.csdeindopdracht.Database.Entity.Race;
import com.example.csdeindopdracht.Database.Entity.RaceRegister;
import com.example.csdeindopdracht.Database.Entity.Runner;
import com.example.csdeindopdracht.Database.Entity.Statistic;
import com.example.csdeindopdracht.Database.Entity.Training;
import com.example.csdeindopdracht.Database.Entity.UserSettings;

@androidx.room.Database(entities = {Race.class, RaceRegister.class, Runner.class, Statistic.class, Training.class, UserSettings.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    private volatile static Database INSTANCE;

    public abstract UserDao userAccess();
    public abstract AdminDao adminAccess();

    public static Database getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "CsdLocalStorage")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
