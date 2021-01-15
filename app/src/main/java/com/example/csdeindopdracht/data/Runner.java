package com.example.csdeindopdracht.data;

public class Runner {

    public String playerName;
    public int stamina;
    public int speed;
    public int topSpeed;



    public Runner(){

        //TODO: remove testCode{
        playerName = "test";
        stamina = 65;
        speed = 100;
        topSpeed = 90;
        //TODO: }



    }

    public int getTopSpeed() {
        return topSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStamina() {
        return stamina;
    }

    public String getPlayerName() {
        return playerName;
    }
}
