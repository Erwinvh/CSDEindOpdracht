package com.example.csdeindopdracht.services.ors.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Summary {
    private double distance;
    private int duration;

    public Summary(double distance, int duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public Summary(JSONObject json) throws JSONException {
        this.distance = json.getDouble("distance");
        this.duration = json.getInt("duration");
    }

    public double getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }
}
