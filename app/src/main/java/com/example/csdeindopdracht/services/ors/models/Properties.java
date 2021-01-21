package com.example.csdeindopdracht.services.ors.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Properties {

    private ArrayList<Segment> segments;
    private Summary summary;
    private int[] wayPoints;

    public Properties(ArrayList<Segment> segments, Summary summary, int[] wayPoints) {
        this.segments = segments;
        this.summary = summary;
        this.wayPoints = wayPoints;
    }

    public Properties(JSONObject json) throws JSONException {
        JSONArray jsonSegments = json.getJSONArray("segments");
        this.segments = new ArrayList<>(jsonSegments.length());
        for (int i = 0; i < jsonSegments.length(); i++) {
            this.segments.add(new Segment(jsonSegments.getJSONObject(i)));
        }

        this.summary = new Summary(json.getJSONObject("summary"));

        JSONArray jsonWayPoints = json.getJSONArray("way_points");
        this.wayPoints = new int[jsonWayPoints.length()];
        for (int i = 0; i < jsonWayPoints.length(); i++) {
            this.wayPoints[i] = jsonWayPoints.getInt(i);
        }
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public Summary getSummary() {
        return summary;
    }

    public int[] getWayPoints() {
        return wayPoints;
    }
}
