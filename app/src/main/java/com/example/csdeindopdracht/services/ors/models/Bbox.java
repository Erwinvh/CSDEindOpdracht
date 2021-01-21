package com.example.csdeindopdracht.services.ors.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.osmdroid.util.GeoPoint;

public class Bbox {

    private GeoPoint point1;
    private GeoPoint point2;

    public Bbox(GeoPoint point1, GeoPoint point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Bbox(JSONArray json) throws JSONException {
        this.point1 = new GeoPoint(json.getDouble(1), json.getDouble(0));
        this.point2 = new GeoPoint(json.getDouble(3), json.getDouble(2));
    }

    public GeoPoint getPoint1() {
        return point1;
    }

    public GeoPoint getPoint2() {
        return point2;
    }
}
