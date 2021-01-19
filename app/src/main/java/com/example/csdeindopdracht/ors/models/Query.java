package com.example.csdeindopdracht.ors.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class Query {

    private ArrayList<GeoPoint> coordinates;
    private String profile;
    private String format;

    public Query(ArrayList<GeoPoint> coordinates, String profile, String format) {
        this.coordinates = coordinates;
        this.profile = profile;
        this.format = format;
    }

    public Query(JSONObject json) throws JSONException {
        JSONArray jsonCoordinates = json.getJSONArray("coordinates");
        this.coordinates = new ArrayList<>(jsonCoordinates.length());
        for (int i = 0; i < jsonCoordinates.length(); i++) {
            this.coordinates.add(new GeoPoint(
                    jsonCoordinates.getJSONArray(i).getDouble(1),
                    jsonCoordinates.getJSONArray(i).getDouble(0)
            ));
        }
        this.profile = json.getString("profile");
        this.format = json.getString("format");
    }

    public ArrayList<GeoPoint> getCoordinates() {
        return coordinates;
    }

    public String getProfile() {
        return profile;
    }

    public String getFormat() {
        return format;
    }
}
