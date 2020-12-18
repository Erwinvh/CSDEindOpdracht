package com.example.csdeindopdracht.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.csdeindopdracht.Database.Entity.Race;
import com.example.csdeindopdracht.Database.Entity.RaceRegister;
import com.example.csdeindopdracht.Database.Entity.Runner;
import com.example.csdeindopdracht.Database.Entity.Statistic;
import com.example.csdeindopdracht.Database.Entity.Training;
import com.example.csdeindopdracht.Database.Entity.UserSettings;

@Dao
public interface AdminDao {

    //Inserts to add to the database
    @Insert
    void addRaces(Race... races);

    @Insert
    void addRunners(Runner... runners);

    @Insert
    void addStatistics(Statistic... statistics);

    @Insert
    void addTrainings(Training... trainings);

    @Insert
    void addUserSettings(UserSettings... userSettings);

    @Insert
    void addRaceRegisters(RaceRegister... registers);

    //Updates to modify existing data in the database
    @Update
    void updateRaces(Race... races);

    @Update
    void updateRunners(Runner... runners);

    @Update
    void updateStatistics(Statistic... statistics);

    @Update
    void updateTrainings(Training... trainings);

    @Update
    void updateUserSettings(UserSettings... userSettings);

    @Update
    void updateRaceRegisters(RaceRegister... registers);

    //Deletes to remove existing data from the database.
    @Delete
    void deleteRaces(Race... races);

    @Delete
    void deleteRunners(Runner... runners);

    @Delete
    void deleteStatistics(Statistic... statistics);

    @Delete
    void deleteTrainings(Training... trainings);

    @Delete
    void deleteUserSettings(UserSettings... userSettings);

    @Delete
    void deleteRaceRegisters(RaceRegister... registers);
}
