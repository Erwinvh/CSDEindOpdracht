package com.example.csdeindopdracht.Database.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.csdeindopdracht.Database.Entity.Runner;
import com.example.csdeindopdracht.Database.Entity.Statistic;

/**
 * Room relation which relates a Race to two or more runners.
 */
public class RunnerStatistics {
    @Embedded
    private Runner runner;

    @Relation(
            parentColumn = "Statistic_ID",
            entityColumn = "id"
    )
    private Statistic statistic;

    public RunnerStatistics(Runner runner, Statistic statistic) {
        this.runner = runner;
        this.statistic = statistic;
    }

    public Runner getRunner() {
        return runner;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }
}
