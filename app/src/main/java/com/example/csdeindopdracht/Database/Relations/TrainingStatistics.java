package com.example.csdeindopdracht.Database.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.csdeindopdracht.Database.Entity.Statistic;
import com.example.csdeindopdracht.Database.Entity.Training;

/**
 * Room relation which relates a Race to two or more runners.
 */
public class TrainingStatistics {

    @Embedded
    private Training training;

    @Relation(
            parentColumn = "Statistic_ID",
            entityColumn = "id"
    )
    private Statistic statistic;

    public TrainingStatistics(Training training, Statistic statistic) {
        this.training = training;
        this.statistic = statistic;
    }

    public Training getTraining() {
        return training;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }
}
