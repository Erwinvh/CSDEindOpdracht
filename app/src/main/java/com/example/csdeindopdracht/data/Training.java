package com.example.csdeindopdracht.data;

import android.content.Context;

import com.example.csdeindopdracht.Logic.MainViewModel;
import com.example.csdeindopdracht.services.gps.Gps;

import java.util.ArrayList;
import java.util.Date;

public class Training {

    private final Gps gps;

    private ArrayList<GpsLocation> route;
    private Date date;

    private Runner runner;


    public Training(Context context) {
        this.gps = new Gps(context);
        this.route = new ArrayList<>();
        this.route.add(new GpsLocation(gps.getGpsLocation().getLocation(), gps.getGpsLocation().getTimestamp()));
        this.date = new Date(System.currentTimeMillis());
        this.runner = null;
    }

    public void updateLocation() {
        GpsLocation location = new GpsLocation(gps.getGpsLocation().getLocation(), gps.getGpsLocation().getTimestamp());

        this.route.add(location);
    }

    public void finaliseTraining(Runner runner) {
        this.runner = runner;
    }

    public ArrayList<GpsLocation> getRoute() {
        return route;
    }

    public Date getDate() {
        return date;
    }

    public boolean isFinalised() {
        return this.runner != null;
    }
}
