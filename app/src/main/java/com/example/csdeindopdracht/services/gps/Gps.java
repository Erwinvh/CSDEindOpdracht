package com.example.csdeindopdracht.services.gps;

import android.Manifest;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.csdeindopdracht.Logic.MainViewModel;
import com.example.csdeindopdracht.data.GpsLocation;
import com.example.csdeindopdracht.services.Notification;

import org.osmdroid.util.GeoPoint;

public class Gps implements LocationListener {

    private static final int GPS_NOTIFICATION = 1;
    private final String TAG = this.getClass().getSimpleName();

    private Context context;

    private GpsLocation gpsLocation;
    private LocationManager locationManager;

    public Gps(Context context) {
        this.gpsLocation = new GpsLocation(
                null,
                System.currentTimeMillis()
        );

        this.context = context;

        this.locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling ActivityCompat#requestPermissions
            Log.e(TAG, "Has no GPS permission.");
        }
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    public GpsLocation getGpsLocation() {
        return gpsLocation;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (location != null) {
            Log.d(TAG, "onLocationChanged. Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude());

            gpsLocation = new GpsLocation(
                    new GeoPoint(location.getLatitude(), location.getLongitude()),
                    System.currentTimeMillis()
            );
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(GPS_NOTIFICATION);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        context.getSystemService(NotificationManager.class).notify(
                GPS_NOTIFICATION,
                Notification.createNotification(context,
                        "has no GPS",
                        "Please enable your GPS to continue your training progress.",
                        new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                ).build()
        );
    }
}
