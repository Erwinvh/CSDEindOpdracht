package com.example.csdeindopdracht.Logic;

import com.example.csdeindopdracht.Database.Entity.Training;

import java.time.LocalDateTime;

public class TrainingLogic {

    private Training currentTraining;


    public void calculateStatistics(){

    }

    public void startNewTraining(){
        currentTraining = new Training(LocalDateTime.now().toString(), "", 0);
        //TODO: preset stuff and initial save to database?
    }


    public void stopCurrentTraining(){
        //TODO: save to database with the final values
        currentTraining = null;
    }

    public Training getCurrentTraining() {
        return currentTraining;
    }
}
