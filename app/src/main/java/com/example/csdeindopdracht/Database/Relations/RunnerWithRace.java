package com.example.csdeindopdracht.Database.Relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.csdeindopdracht.Database.Entity.Race;
import com.example.csdeindopdracht.Database.Entity.RaceRegister;
import com.example.csdeindopdracht.Database.Entity.Runner;

import java.util.List;

public class RunnerWithRace {

    @Embedded
    private Runner runner;

    @Relation(
            parentColumn = "name",
            entityColumn = "id",
            associateBy = @Junction(RaceRegister.class)
    )
    private List<Race> races;

    public RunnerWithRace(Runner runner, List<Race> races) {
        this.runner = runner;
        this.races = races;
    }

    public Runner getRunner() {
        return runner;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }
}
