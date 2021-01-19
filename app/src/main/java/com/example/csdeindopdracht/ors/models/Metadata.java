package com.example.csdeindopdracht.ors.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class Metadata {

    private String attribution;
    private String service;
    private Date timestamp;
    private Query query;
    private Engine engine;

    public Metadata(String attribution, String service, long timestamp, Query query, Engine engine) {
        this.attribution = attribution;
        this.service = service;
        this.timestamp = new Date(timestamp);
        this.query = query;
        this.engine = engine;
    }

    public Metadata(JSONObject json) throws JSONException {
        this.attribution = json.getString("attribution");
        this.service = json.getString("service");
        this.timestamp = new Date(json.getLong("timestamp"));
        this.query = new Query(json.getJSONObject("query"));
        this.engine = new Engine(json.getJSONObject("engine"));
    }

    public String getAttribution() {
        return attribution;
    }

    public String getService() {
        return service;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Query getQuery() {
        return query;
    }

    public Engine getEngine() {
        return engine;
    }
}
