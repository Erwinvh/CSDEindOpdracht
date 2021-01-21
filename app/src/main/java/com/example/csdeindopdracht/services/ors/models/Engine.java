package com.example.csdeindopdracht.services.ors.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Engine {

    private String version;
    private String buildDate;
    private String graphDate;

    public Engine(String version, String buildDate, String graphDate) {
        this.version = version;
        this.buildDate = buildDate;
        this.graphDate = graphDate;
    }

    public Engine(JSONObject json) throws JSONException {
        this.version = json.getString("version");
        this.buildDate = json.getString("build_date");
        this.graphDate = json.getString("graph_date");
    }

    public String getVersion() {
        return version;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public String getGraphDate() {
        return graphDate;
    }
}
