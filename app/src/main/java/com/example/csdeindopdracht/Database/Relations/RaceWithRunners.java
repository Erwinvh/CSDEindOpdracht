package com.example.csdeindopdracht.Database.Relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.csdeindopdracht.Database.Entity.Race;
import com.example.csdeindopdracht.Database.Entity.RaceRegister;
import com.example.csdeindopdracht.Database.Entity.Runner;

import java.util.List;

/**
 * Room relation which relates a Race to two or more runners.
 */
public class RaceWithRunners {
    @Embedded
    private Race race;

    @Relation(
            parentColumn = "id",
            entityColumn = "name",
            associateBy = @Junction(RaceRegister.class)
    )
    private List<Runner> runners;

    public RaceWithRunners(Race race, List<Runner> runners) {
        this.race = race;
        this.runners = runners;
    }

    public Race getRace() {
        return race;
    }

    public List<Runner> getRunners() {
        return runners;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public void setRunners(List<Runner> runners) {
        this.runners = runners;
    }
}
