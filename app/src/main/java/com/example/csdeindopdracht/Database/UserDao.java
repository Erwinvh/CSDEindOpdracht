package com.example.csdeindopdracht.Database;

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

    @Query("SELECT * FROM usersetting WHERE settingID LIKE :settingId")
    UserSettings getUserSettings(String settingId);

    @Transaction
    @Query("SELECT * FROM runner")
    List<RunnerStatistics> getRunnerStatistics();

    @Transaction
    @Query("SELECT * FROM runner WHERE name LIKE :runnerName")
    RunnerStatistics getRunnerStatistics(String runnerName);

    @Transaction
    @Query("SELECT * FROM training")
    List<TrainingStatistics> getTrainingStatistics();

    @Transaction
    @Query("SELECT * FROM training WHERE dateTime LIKE :trainingDate")
    TrainingStatistics getTrainingStatistics(String trainingDate);

    @Transaction
    @Query("SELECT * FROM runner")
    List<RunnerWithRace> getRunnersWithRace();

    @Transaction
    @Query("SELECT * FROM runner WHERE name LIKE :runnerName")
    RunnerWithRace getRunnerRaces(String runnerName);

    @Transaction
    @Query("SELECT * FROM race")
    List<RaceWithRunners> getRacesWithRunners();

    @Transaction
    @Query("SELECT * FROM race WHERE id LIKE :raceId")
    RaceWithRunners getRaceRunners(String raceId);
}
