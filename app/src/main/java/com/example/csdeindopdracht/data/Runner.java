package com.example.csdeindopdracht.data;

public class Runner {

    public String playerName;
    public int stamina;
    public int speed;
    public int topSpeed;

    public Runner(String playerName, int stamina, int speed, int topSpeed) {
        this.playerName = playerName;
        this.stamina = stamina;
        this.speed = speed;
        this.topSpeed = topSpeed;
        moreThanZero();
    }

    public Runner(int stamina, int speed, int topSpeed) {
        this.playerName = "";
        this.stamina = stamina;
        this.speed = speed;
        this.topSpeed = topSpeed;
        moreThanZero();
    }

    public Runner() {
        playerName = "Test Runner";
        stamina = 65;
        speed = 100;
        topSpeed = 90;
    }

    private void moreThanZero() {
        if (this.stamina == 0) this.stamina = 1;
        if (this.speed == 0) this.speed = 1;
        if (this.topSpeed == 0) this.topSpeed = 1;
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

    @Override
    public String toString() {
        return "Runner{" +
                "playerName='" + playerName + '\'' +
                ", stamina=" + stamina +
                ", speed=" + speed +
                ", topSpeed=" + topSpeed +
                '}';
    }
}
