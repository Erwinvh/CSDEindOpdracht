package com.example.csdeindopdracht.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.csdeindopdracht.Database.Entity.UserSettings;
import com.example.csdeindopdracht.Database.Relations.RaceWithRunners;
import com.example.csdeindopdracht.Database.Relations.RunnerStatistics;
import com.example.csdeindopdracht.Database.Relations.RunnerWithRace;
import com.example.csdeindopdracht.Database.Relations.TrainingStatistics;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM usersetting WHERE settingID LIKE 1")
    LiveData<UserSettings> getUserSettings();

    @Transaction
    @Query("SELECT * FROM runner")
    LiveData<List<RunnerStatistics>> getRunnerStatistics();

    @Transaction
    @Query("SELECT * FROM runner WHERE name LIKE :runnerName")
    LiveData<RunnerStatistics> getRunnerStatistics(String runnerName);

    @Transaction
    @Query("SELECT * FROM training")
    LiveData<List<TrainingStatistics>> getTrainingStatistics();

    @Transaction
    @Query("SELECT * FROM training WHERE dateTime LIKE :trainingDate")
    LiveData<TrainingStatistics> getTrainingStatistics(String trainingDate);

    @Transaction
    @Query("SELECT * FROM runner")
    LiveData<List<RunnerWithRace>> getRunnersWithRace();

    @Transaction
    @Query("SELECT * FROM runner WHERE name LIKE :runnerName")
    LiveData<RunnerWithRace> getRunnerRaces(String runnerName);

    @Transaction
    @Query("SELECT * FROM race")
    LiveData<List<RaceWithRunners>> getRacesWithRunners();

    @Transaction
    @Query("SELECT * FROM race WHERE id LIKE :raceId")
    LiveData<RaceWithRunners> getRaceRunners(String raceId);
}
