package com.example.csdeindopdracht.Database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes a runner.
 */
@Entity(tableName = "Runner", indices = {@Index("name"), @Index(value = {"IsPlayer", "IsComplete"})})
public class Runner {

    @NonNull
    @PrimaryKey
    private final String name;

    @NonNull
    @ColumnInfo(name = "PhotoPath")
    private String photoPath;

    @ColumnInfo(name = "IsPlayer")
    private boolean isPlayer;

    @ColumnInfo(name = "IsComplete")
    private boolean isComplete;

    @ForeignKey(entity = Statistic.class, parentColumns = "ID", childColumns = "Statistic_ID", onDelete = ForeignKey.CASCADE)
    @ColumnInfo(name = "Statistic_ID")
    private int statisticsID;

    public Runner(@NonNull String name, @NonNull String photoPath, boolean isPlayer, boolean isComplete, int statisticsID) {
        this.name = name;
        this.photoPath = photoPath;
        this.isPlayer = isPlayer;
        this.isComplete = isComplete;
        this.statisticsID = statisticsID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPhotoPath() {
        return photoPath;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public int getStatisticsID() {
        return statisticsID;
    }

    public void setPhotoPath(@NonNull String photoPath) {
        this.photoPath = photoPath;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setStatisticsID(int statisticsID) {
        this.statisticsID = statisticsID;
    }
}
