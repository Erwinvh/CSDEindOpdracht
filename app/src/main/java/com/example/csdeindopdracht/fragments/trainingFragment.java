package com.example.csdeindopdracht.fragments;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.csdeindopdracht.Logic.MainViewModel;
import com.example.csdeindopdracht.Logic.TrainingLogic;
import com.example.csdeindopdracht.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.security.Timestamp;
import java.util.ArrayList;


public class trainingFragment extends Fragment {

    private MapView mapView;
    private MapController mapController;
    private MyLocationNewOverlay locationOverlay;
    private MainViewModel mainViewModel;
    private Context context;
    private final int ZOOM_LEVEL = 19;
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    //training related
    private TrainingLogic traininglogic;
    private Timestamp startTime;
    private Timestamp currentTime;
    private Timestamp endTime;
    private GeoPoint beginGeoPoint;
    private GeoPoint endGeoPoint;
    private ArrayList<GeoPoint> BetweenPointsList = new ArrayList<>();
    private ArrayList<Timestamp> timesBetweenPoints = new ArrayList<>();

    public trainingFragment() {
        // Required empty public constructor
    }



    @Override
    public void onStart() {
        super.onStart();
        this.mapView.onResume();
        this.locationOverlay.onResume();

        this.mapController.setCenter(locationOverlay.getMyLocation());
        this.mapController.animateTo(locationOverlay.getMyLocation());
        this.mapController.zoomTo(ZOOM_LEVEL);

//        traininglogic.startNewTraining();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mapView.onPause();
        this.locationOverlay.onPause();
       // this.traininglogic.stopCurrentTraining();
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
        return inflater.inflate(R.layout.fragment_training, container, false);
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

        this.mapView = getActivity().findViewById(R.id.training_map);
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




    }

    public MyLocationNewOverlay getLocationOverlay() {
        return locationOverlay;
    }

    public void StopChecking(){
//        if(gpsLogic != null){
//            gpsLogic.stop();
//        }
    }

}