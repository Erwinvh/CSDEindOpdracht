package com.example.csdeindopdracht.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.csdeindopdracht.Logic.MainViewModel;
import com.example.csdeindopdracht.Logic.RaceLogic;
import com.example.csdeindopdracht.MainActivity;
import com.example.csdeindopdracht.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;


public class raceFragment extends Fragment {

    //Map related
    private MapView mapView;
    private MapController mapController;
    private MyLocationNewOverlay locationOverlay;

    //Info related
    private MainViewModel mainViewModel;

    private boolean racestarted = false;

    //Race related
    private GeoPoint beginGeoPoint;
    private GeoPoint endGeoPoint;
    private GeoPoint playerGeoPoint;
    private GeoPoint OpponentGeoPoint;
    private Marker PlayerMarker;
    private Marker OpponentMarker;
    private Marker EndpointMarker;

    private final int ZOOM_LEVEL = 19;
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    //UI related
    private FloatingActionButton SprintButton;

    public raceFragment(MainViewModel mainViewModel) {
        // Required empty public constructor
        this.mainViewModel = mainViewModel;
    }


    @Override
    public void onStart() {
        super.onStart();
        this.mapView.onResume();
        this.locationOverlay.onResume();

        this.mapController.setCenter(locationOverlay.getMyLocation());
        this.mapController.animateTo(locationOverlay.getMyLocation());
        this.mapController.zoomTo(ZOOM_LEVEL);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mapView.onPause();
        this.locationOverlay.onPause();
        //TODO: forfeit race
        mainViewModel.raceLogic.forfeitRace();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_race, container, false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this.getActivity(),
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    final static int GLOBE_WIDTH = 256; // a constant in Google's map projection
    final static int ZOOM_MAX = 21;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        this.mapView = getActivity().findViewById(R.id.race_map);
        this.mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        this.mapView.setMultiTouchControls(true);
        this.mapView.setTileSource(TileSourceFactory.MAPNIK);
        this.locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getActivity().getApplicationContext()), this.mapView);
        this.locationOverlay.enableMyLocation();
        this.locationOverlay.enableFollowLocation();
        this.mapView.getOverlays().add(this.locationOverlay);
        this.mapController = new MapController(this.mapView);
        this.mapController.setCenter(locationOverlay.getMyLocation());
        this.mapController.animateTo(locationOverlay.getMyLocation());
        this.mapController.zoomTo(ZOOM_LEVEL);

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        SprintButton = view.findViewById(R.id.sprint_button);
        SprintButton.setOnClickListener(v -> {
            if(!racestarted) {
                beginGeoPoint = new GeoPoint(locationOverlay.getMyLocation().getLatitude(), locationOverlay.getMyLocation().getLongitude());
                playerGeoPoint = locationOverlay.getMyLocation();
                OpponentGeoPoint = locationOverlay.getMyLocation();
                DrawBeginPoint(beginGeoPoint);
                Drawplayer(playerGeoPoint);
                DrawOponent(OpponentGeoPoint);
                endGeoPoint = CalculateEndPoint();
                DrawEndPoint(endGeoPoint);
                mainViewModel.getRaceLogic().startRace(beginGeoPoint, endGeoPoint, this);

                Toast.makeText(getActivity(), R.string.Readysetgo,
                        Toast.LENGTH_SHORT).show();
                racestarted = true;
                SprintButton.setImageResource(R.drawable.stop_foreground);
            }else{
                //TODO: Toast that you gave up
                Toast.makeText(getActivity(), R.string.ForfeitRace,
                        Toast.LENGTH_SHORT).show();
                mainViewModel.raceLogic.forfeitRace();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mapView.getOverlays().clear();
                racestarted = false;
                SprintButton.setImageResource(R.drawable.play_foreground);
            }

        });

    }

    private GeoPoint CalculateEndPoint() {
        Random r = new Random();
        int low = 10;
        int high = 40;
        double resultLat = (r.nextInt(high - low) + low) / 10000.0;
        double resultLong = (r.nextInt(high - low) + low) / 10000.0;

        double LongDoubleValue = Math.random();
        int LongIntValue = (int) Math.round(LongDoubleValue);
        double LatDoubleValue = Math.random();
        int LatIntValue = (int) Math.round(LatDoubleValue);
        if (LongIntValue == 0) {
            resultLong = -resultLong;
        }
        if (LatIntValue == 0) {
            resultLat = -resultLat;
        }

        return new GeoPoint(locationOverlay.getMyLocation().getLatitude() + resultLat, locationOverlay.getMyLocation().getLongitude() + resultLong);
    }

    public void DrawRoute(ArrayList<GeoPoint> geoPoints) {
        Polyline line = new Polyline();
        line.setPoints(geoPoints);
        mapView.getOverlayManager().add(line);
    }


    public MyLocationNewOverlay getLocationOverlay() {
        return locationOverlay;
    }

    public void StopChecking() {
//        if(gpsLogic != null){
//            gpsLogic.stop();
//        }
    }

//    public int measureDistance(ArrayList<Coordinate> coordinates){
//        int total = 0;
//        for (int i = 0; i<coordinates.size()-1;i++){
//            GeoPoint geoPoint = new GeoPoint(coordinates.get(i).getLatitude(),coordinates.get(i).getLongitude());
//            GeoPoint geoPoint2 = new GeoPoint(coordinates.get(i+1).getLatitude(),coordinates.get(i+1).getLongitude());
//            total = total + (int)(geoPoint.distanceToAsDouble(geoPoint2));
//        }
//        return total;
//    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void DrawBeginPoint(GeoPoint geoPoint) {

        Marker marker = new Marker(mapView);
        marker.setTitle("BeginPoint");
        marker.setPosition(geoPoint);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//            marker.setIcon(getResources().getDrawable(R.drawable.training_foreground, context.getTheme()));
        mapView.getOverlays().add(marker);
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public void DrawEndPoint(GeoPoint geoPoint) {

        EndpointMarker = new Marker(mapView);
        EndpointMarker.setTitle("EndPoint");
        EndpointMarker.setPosition(geoPoint);
        EndpointMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        EndpointMarker.setIcon(getResources().getDrawable(R.drawable.finishline_foreground, getContext().getTheme()));
        mapView.getOverlays().add(EndpointMarker);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void DrawOponent(GeoPoint geoPoint) {

        OpponentMarker = new Marker(mapView);
        OpponentMarker.setTitle("Opponent");
        OpponentMarker.setPosition(geoPoint);
        OpponentMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//        Drawable opponentimage = getResources().getDrawable(getResources().getIdentifier(mainViewModel.getCurrentOpponentImage(), "drawable",
//              getContext().getPackageName()));
//        OpponentMarker.setIcon(getResources().getDrawable(R.drawable.opponent1, getContext().getTheme()));
        mapView.getOverlays().add(OpponentMarker);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void Drawplayer(GeoPoint geoPoint) {

        PlayerMarker = new Marker(mapView);
        PlayerMarker.setTitle("Player");
        PlayerMarker.setPosition(geoPoint);
        PlayerMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
//        Drawable opponentimage = getResources().getDrawable(getResources().getIdentifier(mainViewModel.getCurrentOpponentImage(), "drawable",
//                getContext().getPackageName()));
//        PlayerMarker.setIcon(getResources().getDrawable(R.drawable.opponent1, getContext().getTheme()));
        mapView.getOverlays().add(PlayerMarker);
    }

    public void setPlayerMarker(GeoPoint myLocation) {
        mapView.getOverlays().remove(PlayerMarker);
        Drawplayer(myLocation);
    }

    public void setOpponentMarker(GeoPoint opponentLocation) {
        mapView.getOverlays().remove(OpponentMarker);
        DrawOponent(opponentLocation);
    }

    public void setEndPointMarker(GeoPoint EndPointLocation){
        mapView.getOverlays().remove(EndpointMarker);
        DrawEndPoint(EndPointLocation);
    }
}

