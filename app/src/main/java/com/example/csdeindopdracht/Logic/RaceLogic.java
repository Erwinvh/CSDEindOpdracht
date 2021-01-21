package com.example.csdeindopdracht.Logic;

import android.content.Context;

import com.example.csdeindopdracht.Database.Entity.Runner;
import com.example.csdeindopdracht.Database.Relations.RunnerStatistics;
import com.example.csdeindopdracht.fragments.raceFragment;
import com.example.csdeindopdracht.ors.DirectionsPost;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MapTileIndex;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;

public class RaceLogic {

    public Context context;
    public Thread RacingThread;
    public boolean on;
    public ArrayList<GeoPoint> RaceRoute = new ArrayList<>();
    private GeoPoint endPoint;
    private MainViewModel mainViewModel;
    private raceFragment RaceFragment;

    private double BaseSpeed = 0.000001;
    private double scaleDifference = 0.00001;
    //RACE Player ITEMS
    public boolean isPlayerBoosted = false;
    public int playerboostCounter = 0;
    private GeoPoint MyLocation;
    private double anglePlayer;
    private int xFactorPlayer;
    private int lastCheckPointPlayer = 0;
    private int playerBoostedAmount = 0;

    //RACE Opponent ITEMS
    public boolean isOponentBoosted = false;
    public int oponentboostCounter = 0;
    private GeoPoint opponentLocation;
    private double angleOpponent;
    private int xFactorOpponent;
    private int lastCheckPointOpponent = 0;
    private int opponentBoostedAmount = 0;

    public RaceLogic(Context context, MainViewModel mainViewModel) {
        this.context = context;
        this.mainViewModel = mainViewModel;
    }


    public void startRace(GeoPoint begin, GeoPoint end, raceFragment fragment) {
        DirectionsPost.executeRaceRoute(context, begin, end, this);
        this.endPoint = end;
        this.MyLocation = begin;
        this.opponentLocation = begin;
        this.RaceFragment = fragment;

        this.on = true;

    }

    public void ConstantlyCheck() {
        while (on) {
            procedure();

            //TODO: turn this to geofencing
            double a = MyLocation.distanceToAsDouble(endPoint);
            double a2 = MyLocation.distanceToAsDouble(RaceRoute.get(RaceRoute.size()-1));
            double b = opponentLocation.distanceToAsDouble(endPoint);
            double b2 = opponentLocation.distanceToAsDouble(RaceRoute.get(RaceRoute.size()-1));

            if (a <= 15.0 || a2<= 15.0) {
                CrossedFinishLine(true);
            } else if (b <= 15.0 || b2 <= 15.0) {
                CrossedFinishLine(false);
            }

        }
    }


    public void procedure() {
        try {
            Thread.sleep(100);
            CalculatePlayerPosition();
            SetOpponentBooster();
            CalculateOponentPosition();
            if (checkCheckpointProgress(lastCheckPointPlayer, MyLocation)) {
                if (lastCheckPointPlayer + 1 < RaceRoute.size()) {
                    lastCheckPointPlayer++;
                    xFactorPlayer = CalculateXFactor(lastCheckPointPlayer);
                    anglePlayer = CalculateAngle(lastCheckPointPlayer);
                }
                MyLocation.setCoords(RaceRoute.get(lastCheckPointPlayer).getLatitude(), RaceRoute.get(lastCheckPointPlayer).getLongitude());
            }
            if (checkCheckpointProgress(lastCheckPointOpponent, opponentLocation)) {
                if (lastCheckPointOpponent + 1 < RaceRoute.size()) {
                    lastCheckPointOpponent++;
                    xFactorOpponent = CalculateXFactor(lastCheckPointOpponent);
                    angleOpponent = CalculateAngle(lastCheckPointOpponent);
                }
                opponentLocation.setCoords(RaceRoute.get(lastCheckPointOpponent).getLatitude(), RaceRoute.get(lastCheckPointOpponent).getLongitude());

            }
        } catch (InterruptedException | NullPointerException e) {
            on = false;
        }
    }

    private void SetOpponentBooster() {
        if (opponentBoostedAmount < mainViewModel.getOpponentStamina()) {
            int result = (int)(Math.random() * (20 - 1) + 1);
            if (result == 20) {
                isOponentBoosted = true;
                opponentBoostedAmount++;
            }
        }
    }


    public void CalculatePlayerPosition() {
        double newLat = MyLocation.getLatitude();
        double newLong = MyLocation.getLongitude();
        System.out.println("old lat: " + newLat + " angle: " + anglePlayer +"");
        //Update lat and long
        if (xFactorPlayer != 0) {
            double b = newLat - (anglePlayer * newLong);
            if (isPlayerBoosted) {
                newLong += xFactorPlayer * BaseSpeed * mainViewModel.getMaxSpeed();
            } else {
                newLong += xFactorPlayer * BaseSpeed * mainViewModel.getSpeed();
            }
            newLat = anglePlayer * newLong + b;
        } else {
            if (isPlayerBoosted) {
                newLat += BaseSpeed * mainViewModel.getMaxSpeed();
            } else {
                newLat += BaseSpeed * mainViewModel.getSpeed();
            }
        }
        if (isPlayerBoosted) {
            playerboostCounter++;
            if (playerboostCounter > 5) {
                playerboostCounter = 0;
                isPlayerBoosted = false;
            }
        }

        // updates my location
        MyLocation.setCoords(newLat, newLong);
        RaceFragment.setPlayerMarker(MyLocation);

    }

    public void CalculateOponentPosition() {
        double newLat = opponentLocation.getLatitude();
        double newLong = opponentLocation.getLongitude();

        //Update lat and long
        if (xFactorOpponent != 0) {
            double b = newLat - (angleOpponent * newLong);
            if (isOponentBoosted) {
                newLong += xFactorOpponent * BaseSpeed * mainViewModel.getOpponentMaxSpeed();
            } else {
                newLong += xFactorOpponent * BaseSpeed * mainViewModel.getOpponentSpeed();
            }
            newLat = angleOpponent * newLong + b;
        } else {
            if (isOponentBoosted) {
                newLat += BaseSpeed * mainViewModel.getOpponentMaxSpeed();
            } else {
                newLat += BaseSpeed * mainViewModel.getOpponentSpeed();
            }
        }
        if (isOponentBoosted) {
            oponentboostCounter++;
            if (oponentboostCounter > 5) {
                oponentboostCounter = 0;
                isOponentBoosted = false;
            }
        }

        // updates my location
        opponentLocation.setCoords(newLat, newLong);
        RaceFragment.setOpponentMarker(opponentLocation);

    }

    public double CalculateAngle(int lastCheckPoint) {
        GeoPoint firstPoint = RaceRoute.get(lastCheckPoint);
        GeoPoint secondPoint = RaceRoute.get(lastCheckPoint + 1);
        double upper = firstPoint.getLatitude() - secondPoint.getLatitude();
        double lower = (firstPoint.getLongitude() - secondPoint.getLongitude());
        System.out.println("Upper: " + upper + " Lower: " + lower);
        return (upper/lower);
    }

    public int CalculateXFactor(int lastCheckPoint) {
        GeoPoint firstPoint = RaceRoute.get(lastCheckPoint);
        GeoPoint secondPoint = RaceRoute.get(lastCheckPoint + 1);
        double result = secondPoint.getLongitude() - firstPoint.getLongitude();
        if (result < 0) {
            return -1;
        } else if (result == 0) {
            return 0;
        } else {
            return 1;
        }
    }


    public boolean checkCheckpointProgress(int lastCheckpoint, GeoPoint position) {
        GeoPoint nextCheckPoint = RaceRoute.get(lastCheckpoint + 1);
        return nextCheckPoint.getLongitude() - position.getLongitude() < scaleDifference && nextCheckPoint.getLatitude() - position.getLatitude() < scaleDifference;
    }

    public void CrossedFinishLine(Boolean RaceResult) {
        //TODO: finish
        endRace();
        if (RaceResult) {
//          victory!
          mainViewModel.completeRace();
        } else {
//          Lose!
//          ForfeitRace()
        }
        resetRaceValues();
    }

    public void endRace() {
        this.on = false;
    }

    public void UseSprintButton() {
        if (playerBoostedAmount < mainViewModel.getStamina()) {
            isPlayerBoosted = true;
            playerBoostedAmount++;
        } else {
            raceFragment.sendStaminaDepleted();
        }
    }

    public void resetRaceValues() {
        RaceRoute = new ArrayList<>();
        endPoint = null;

        isPlayerBoosted = false;
        playerboostCounter = 0;
        MyLocation = null;
        anglePlayer = 0;
        xFactorPlayer = 0;
        lastCheckPointPlayer = 0;
        playerBoostedAmount = 0;

        isOponentBoosted = false;
        oponentboostCounter = 0;
        opponentLocation = null;
        angleOpponent = 0;
        xFactorOpponent = 0;
        lastCheckPointOpponent = 0;
        opponentBoostedAmount = 0;
    }

    public void setRoute(ArrayList<GeoPoint> coordinates) {
        RaceRoute = coordinates;
        this.RaceFragment.DrawRoute(RaceRoute);
        xFactorPlayer = CalculateXFactor(0);
        anglePlayer = CalculateAngle(0);
        xFactorOpponent = CalculateXFactor(0);
        angleOpponent = CalculateAngle(0);

        this.RacingThread = new Thread(this::ConstantlyCheck);
        this.RacingThread.start();
    }

    public void forfeitRace() {
        on = false;
    }
}
