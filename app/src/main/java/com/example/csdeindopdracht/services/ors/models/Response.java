package com.example.csdeindopdracht.services.ors.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Response {

    private String type;
    private Features features;
    private Bbox bbox;
    private Metadata metadata;

    public Response(String type, Features features, Bbox bbox, Metadata metadata) {
        this.type = type;
        this.features = features;
        this.bbox = bbox;
        this.metadata = metadata;
    }

    public Response(JSONObject json) throws JSONException {
        this.type = json.getString("type");
        this.features = new Features(json.getJSONArray("features").getJSONObject(0));
        this.bbox = new Bbox(json.getJSONArray("bbox"));
        this.metadata = new Metadata(json.getJSONObject("metadata"));
    }

    public String getType() {
        return type;
    }

    public Features getFeatures() {
        return features;
    }

    public Bbox getBbox() {
        return bbox;
    }

    public Metadata getMetadata() {
        return metadata;
    }
}
