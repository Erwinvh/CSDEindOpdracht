package com.example.csdeindopdracht.services.ors.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Step {

    private double distance;
    private int duration;
    private int type;
    private String instruction;
    private String name;
    private int startWaypoint;
    private int endWaypoint;

    public Step(double distance, int duration, int type, String instruction, String name, int[] waypoints) {
        this.distance = distance;
        this.duration = duration;
        this.type = type;
        this.instruction = instruction;
        this.name = name;
        this.startWaypoint = waypoints[0];
        this.endWaypoint = waypoints[1];
    }

    public Step(JSONObject json) throws JSONException {
        this.distance = json.getDouble("distance");
        this.duration = json.getInt("duration");
        this.type = json.getInt("type");
        this.instruction = json.getString("instruction");
        this.name = json.getString("name");
        this.startWaypoint = json.getJSONArray("way_points").getInt(0);
        this.endWaypoint = json.getJSONArray("way_points").getInt(1);
    }

    public double getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public int getType() {
        return type;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getName() {
        return name;
    }

    public int getStartWaypoint() {
        return startWaypoint;
    }

    public int getEndWaypoint() {
        return endWaypoint;
    }
}
