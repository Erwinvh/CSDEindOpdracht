package com.example.csdeindopdracht.Database.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes a statistic.
 */
@Entity(tableName = "statistic", indices = {@Index("id"), @Index(value = {"TopSpeed", "GeneralSpeed"})})
public class Statistic {

    @PrimaryKey
    private final int id;

    @ColumnInfo(name = "TopSpeed")
    private double topSpeed;

    @ColumnInfo(name = "GeneralSpeed")
    private double generalSpeed;

    @ColumnInfo(name = "RunDistance")
    private double runDistance;

    public Statistic(int id, double topSpeed, double generalSpeed, double runDistance) {
        this.id = id;
        this.topSpeed = topSpeed;
        this.generalSpeed = generalSpeed;
        this.runDistance = runDistance;
    }

    public int getId() {
        return id;
    }

    public double getGeneralSpeed() {
        return generalSpeed;
    }

    public double getRunDistance() {
        return runDistance;
    }

    public double getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(double topSpeed) {
        this.topSpeed = topSpeed;
    }

    public void setGeneralSpeed(double generalSpeed) {
        this.generalSpeed = generalSpeed;
    }

    public void setRunDistance(double runDistance) {
        this.runDistance = runDistance;
    }
}
