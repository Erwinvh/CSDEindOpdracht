package com.example.csdeindopdracht.Database.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes a statistic.
 */
@Entity(tableName = "Statistic")
public class Statistic {


    @NonNull
    @PrimaryKey
    private int statID;

    @NonNull
    @ColumnInfo(name = "TopSpeed")
    private double TopSpeed;

    @NonNull
    @ColumnInfo(name = "GeneralSpeed")
    private double GeneralSpeed;

    @NonNull
    @ColumnInfo(name = "RunDistance")
    private double RunDistance;


    public double getGeneralSpeed() {
        return GeneralSpeed;
    }

    public double getRunDistance() {
        return RunDistance;
    }

    public double getTopSpeed() {
        return TopSpeed;
    }

    public int getStatID() {
        return statID;
    }

    public void setGeneralSpeed(double generalSpeed) {
        GeneralSpeed = generalSpeed;
    }

    public void setRunDistance(double runDistance) {
        RunDistance = runDistance;
    }

    public void setTopSpeed(double topSpeed) {
        TopSpeed = topSpeed;
    }
}
