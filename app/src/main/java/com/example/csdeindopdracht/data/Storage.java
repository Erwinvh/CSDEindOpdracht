package com.example.csdeindopdracht.data;

import java.util.LinkedList;

public class Storage {

    public LinkedList<Runner> oponentList;
    public Runner player;
    public int index;
    public LinkedList<Training> trainings;

    public Storage (String name){
        trainings = new LinkedList<>();
        oponentList = new LinkedList<Runner>();
        fillOponenetList();
        player = new Runner();
        index = 0;
    }

    private void fillOponenetList() {







    }


    public Runner getPlayer() {
        return player;
    }
}
