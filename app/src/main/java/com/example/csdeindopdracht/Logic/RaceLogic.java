package com.example.csdeindopdracht.Logic;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;


import com.example.csdeindopdracht.MainActivity;
import com.example.csdeindopdracht.R;
import com.example.csdeindopdracht.fragments.raceFragment;
import com.example.csdeindopdracht.ors.DirectionsPost;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;


public class RaceLogic {

    public Context context;
    public Thread RacingThread;
    public boolean on;
    public ArrayList<GeoPoint> RaceRoute = new ArrayList<>();
    private GeoPoint endPoint;
    private MainViewModel mainViewModel;
    private raceFragment RaceFragment;

    private double BaseSpeed = 0.0000001;
    private double scaleDifference = 0.0000001;
    private double maxLat;
    private double maxLong;
    private double MinLat;
    private double MinLong;
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
        this.MyLocation = begin.clone();
        this.opponentLocation = begin.clone();
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

            if (a <= 5.0 || a2<= 5.0) {
                CrossedFinishLine(true);
            } else if (b <= 5.0 || b2 <= 5.0) {
                CrossedFinishLine(false);
            }

        }
        resetRaceValues();
    }


    public void procedure() {
        try {
            Thread.sleep(10);
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
            OffTrailSafeGuard();

        } catch (NullPointerException | InterruptedException e) {
            on = false;
        }
    }

    private void OffTrailSafeGuard() {
        if (MinLat > MyLocation.getLatitude() || MinLat > opponentLocation.getLatitude() || MinLong > MyLocation.getLongitude() || MinLong > opponentLocation.getLongitude() || maxLat < MyLocation.getLatitude() || maxLat < opponentLocation.getLatitude() || maxLong < MyLocation.getLongitude() || maxLong < opponentLocation.getLongitude()){
            ResetRunnersToLastCheckpoint();
        }
    }

    private void ResetRunnersToLastCheckpoint() {
        MyLocation = RaceRoute.get(lastCheckPointPlayer).clone();
        opponentLocation = RaceRoute.get(lastCheckPointOpponent).clone();
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
        //Check logic
        GeoPoint nextCheckPoint = RaceRoute.get(lastCheckpoint + 1);
        return Math.abs(nextCheckPoint.getLongitude() - position.getLongitude()) < scaleDifference && Math.abs(nextCheckPoint.getLatitude() - position.getLatitude()) < scaleDifference;
    }

    public void CrossedFinishLine(Boolean RaceResult) {
        endRace();
        if (RaceResult) {
            mainViewModel.activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, R.string.WinRaceMessage,Toast.LENGTH_SHORT).show();
                }
            });
          mainViewModel.completeRace();
        } else {
            mainViewModel.activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, R.string.LoseRaceMessage,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void endRace() {
        this.on = false;
    }

    public void UseSprintButton() {
        if (playerBoostedAmount < mainViewModel.getStamina()) {
            isPlayerBoosted = true;
            playerBoostedAmount++;
        } else {
            mainViewModel.activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, R.string.DepletedStamina,Toast.LENGTH_SHORT).show();
                }
            });
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
        GeoPoint lastroute = RaceRoute.get(RaceRoute.size()-1);
        if (lastroute.getLongitude()!= endPoint.getLongitude()||lastroute.getLatitude()!= endPoint.getLatitude()){
            RaceFragment.setEndPointMarker(lastroute);
        }
        xFactorPlayer = CalculateXFactor(0);
        anglePlayer = CalculateAngle(0);
        xFactorOpponent = CalculateXFactor(0);
        angleOpponent = CalculateAngle(0);
        maxLat = Double.MIN_NORMAL;
        maxLong = Double.MIN_NORMAL;
        MinLat = Double.MAX_VALUE;
        MinLong = Double.MAX_VALUE;
        for (GeoPoint point : coordinates){
            if (maxLat < point.getLatitude()){
                maxLat = point.getLatitude();
            }
            if (maxLong < point.getLongitude()){
                maxLong = point.getLongitude();
            }
            if (MinLong > point.getLongitude()){
                MinLong = point.getLongitude();
            }
            if (MinLat > point.getLatitude()){
                MinLat = point.getLatitude();
            }
        }

        this.RacingThread = new Thread(this::ConstantlyCheck);
        this.RacingThread.start();
    }

    public void forfeitRace() {
        on = false;
    }
}
