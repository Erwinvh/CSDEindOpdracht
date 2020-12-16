package com.example.csdeindopdracht.data;

public class Runner {

    public String playerName;
    public int stamina;
    public int speed;
    public int maxSpeed;



    public Runner(){

        //TODO: remove testCode{
        playerName = "test";
        stamina = 10;
        speed = 20;
        maxSpeed = 40;
        //TODO: }



    }

    public int getMaxSpeed() {
        return maxSpeed;
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
