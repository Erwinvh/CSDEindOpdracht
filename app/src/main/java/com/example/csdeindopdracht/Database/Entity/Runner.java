package com.example.csdeindopdracht.Database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes a runner.
 */
@Entity(tableName = "Runner")
public class Runner {

    @NonNull
    @PrimaryKey
    private String RunnerName;

    @NonNull
    @ColumnInfo(name = "PhotoPath")
    private String PhotoPath;

    @NonNull
    @ColumnInfo(name = "isPlayer")
    private boolean isPlayer;

    @NonNull
    @ForeignKey(Statistic.StatID)
    @ColumnInfo(name = "isComplete")
    private boolean isComplete;


    @NonNull
    public String getPhotoPath() {
        return PhotoPath;
    }

    @NonNull
    public String getRunnerName() {
        return RunnerName;
    }

    public void setPhotoPath(@NonNull String photoPath) {
        PhotoPath = photoPath;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    public void setRunnerName(@NonNull String runnerName) {
        RunnerName = runnerName;
    }




}
