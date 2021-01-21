package com.example.csdeindopdracht.services.ors.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Segment {

    private double distance;
    private int duration;
    private ArrayList<Step> steps;

    public Segment(double distance, int duration, ArrayList<Step> steps) {
        this.distance = distance;
        this.duration = duration;
        this.steps = steps;
    }

    public Segment(JSONObject json) throws JSONException {
        this.distance = json.getDouble("distance");
        this.duration = json.getInt("duration");
        JSONArray jsonSteps = json.getJSONArray("steps");
        this.steps = new ArrayList<>(jsonSteps.length());
        for (int i = 0; i < jsonSteps.length(); i++) {
            this.steps.add(new Step(jsonSteps.getJSONObject(i)));
        }
    }

    public double getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }
}
