package com.example.csdeindopdracht.Database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes a training.
 */
@Entity(tableName = "Training")
public class Training {

    @NonNull
    @PrimaryKey
    private String DateTime;

    @NonNull
    @ColumnInfo(name = "Time")
    private String Time;

    @NonNull
    @ForeignKey(Statistic.statID)
    @ColumnInfo(name = "StatID")
    private double StatID;

    public double getStatID() {
        return StatID;
    }

    @NonNull
    public String getDateTime() {
        return DateTime;
    }

    @NonNull
    public String getTime() {
        return Time;
    }

    public void setDateTime(@NonNull String dateTime) {
        DateTime = dateTime;
    }

    public void setTime(@NonNull String time) {
        Time = time;
    }
}
