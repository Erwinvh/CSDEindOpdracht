package com.example.csdeindopdracht.data;

import org.osmdroid.util.GeoPoint;

public class GpsLocation {

    private GeoPoint location;
    private long timestamp;

    public GpsLocation(GeoPoint location, long timestamp) {
        this.location = location;
        this.timestamp = timestamp;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
