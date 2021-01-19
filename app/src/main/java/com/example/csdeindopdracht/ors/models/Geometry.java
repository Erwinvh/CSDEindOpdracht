package com.example.csdeindopdracht.ors.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class Geometry {

    private ArrayList<GeoPoint> coordinates;
    private String type;

    public Geometry(ArrayList<GeoPoint> coordinates, String type) {
        this.coordinates = coordinates;
        this.type = type;
    }

    public Geometry(JSONObject json) throws JSONException {
        JSONArray jsonCoordinates = json.getJSONArray("coordinates");
        this.coordinates = new ArrayList<>(jsonCoordinates.length());
        for (int i = 0; i < jsonCoordinates.length(); i++) {
            this.coordinates.add(new GeoPoint(
                    jsonCoordinates.getJSONArray(i).getDouble(1),
                    jsonCoordinates.getJSONArray(i).getDouble(0)
            ));
        }
        this.type = json.getString("type");
    }

    public ArrayList<GeoPoint> getCoordinates() {
        return coordinates;
    }

    public String getType() {
        return type;
    }
}
