package com.example.csdeindopdracht.Database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes a training.
 */
@Entity(tableName = "Training", indices = {@Index("dateTime"), @Index(value = {"Time"})})
public class Training {

    @NonNull
    @PrimaryKey
    private String dateTime;

    @NonNull
    @ColumnInfo(name = "Time")
    private String time;

    @ForeignKey(entity = Statistic.class, parentColumns = "ID", childColumns = "Statistic_ID", onDelete = ForeignKey.CASCADE)
    @ColumnInfo(name = "Statistic_ID")
    private int statisticID;

    public Training(@NonNull String dateTime, @NonNull String time, int statisticID) {
        this.dateTime = dateTime;
        this.time = time;
        this.statisticID = statisticID;
    }

    @NonNull
    public String getDateTime() {
        return dateTime;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public int getStatisticID() {
        return statisticID;
    }

    public void setDateTime(@NonNull String dateTime) {
        this.dateTime = dateTime;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    public void setStatisticID(int statisticID) {
        this.statisticID = statisticID;
    }
}
