package com.example.csdeindopdracht.ors.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Features {

    private Bbox bbox;
    private String type;
    private Properties properties;
    private Geometry geometry;

    public Features(Bbox bbox, String type, Properties properties, Geometry geometry) {
        this.bbox = bbox;
        this.type = type;
        this.properties = properties;
        this.geometry = geometry;
    }

    public Features(JSONObject json) throws JSONException {
        this.bbox = new Bbox(json.getJSONArray("bbox"));
        this.type = json.getString("type");
        this.properties = new Properties(json.getJSONObject("properties"));
        this.geometry = new Geometry(json.getJSONObject("geometry"));
    }

    public Bbox getBbox() {
        return bbox;
    }

    public String getType() {
        return type;
    }

    public Properties getProperties() {
        return properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
