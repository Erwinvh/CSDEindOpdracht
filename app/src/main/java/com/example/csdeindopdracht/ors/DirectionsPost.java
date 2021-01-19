package com.example.csdeindopdracht.ors;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.csdeindopdracht.R;
import com.example.csdeindopdracht.ors.models.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DirectionsPost {

    private final static String TAG = "DirectionsPost.class";
    private final static String BASE_URL = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";
    private final static MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Response response;

    public DirectionsPost() {
        // Cannot be initialised.
    }

    private void initialise(JSONObject response) throws JSONException {
        this.response = new Response(response);
    }

    public static void executeExample(Context context) {
        ArrayList<GeoPoint> route = new ArrayList<>();
        route.add(new GeoPoint(51.587509, 4.780056));
        route.add(new GeoPoint(51.589609, 4.776996));
        route.add(new GeoPoint(51.589282, 4.774282));
        route.add(new GeoPoint(51.588335, 4.776278));
        route.add(new GeoPoint(51.586602, 4.776128));
        route.add(new GeoPoint(51.586882, 4.779325));
        route.add(new GeoPoint(51.587509, 4.780056));
        new DirectionsPost.Builder(context, route).call(apiResponse -> {
            Log.d(TAG, "The route has " + apiResponse.response.getFeatures().getGeometry().getCoordinates().size() + " coordinates.");
        });
    }

    public Response getResponse() {
        return this.response;
    }

    public static class Builder {

        private Context context;
        private ArrayList<GeoPoint> coordinates;

        public Builder(Context context, GeoPoint startPoint, GeoPoint endPoint) {
            this.context = context;
            this.coordinates = new ArrayList<>();
            this.coordinates.add(startPoint);
            this.coordinates.add(endPoint);
        }

        public Builder(Context context, ArrayList<GeoPoint> geoPoints) {
            this.context = context;
            this.coordinates = geoPoints;
        }

        @Deprecated
        public Builder addCoordinate(GeoPoint coordinate) {
            this.coordinates.add(coordinate);
            return this;
        }

        private RequestBody buildBody() {
            StringBuilder body = new StringBuilder();

            // Add coordinates
            body.append("\"coordinates\":[");
            for (int i = 0; i < coordinates.size(); i++) {
                body.append('[')
                        .append(coordinates.get(i).getLongitude())
                        .append(',')
                        .append(coordinates.get(i).getLatitude())
                        .append(']');
                if (coordinates.size() != i + 1) {
                    body.append(',');
                }
            }
            body.append(']');

            body.insert(0, '{').append('}');
            Log.d(TAG, "Body json: " + body.toString());
            return RequestBody.create(body.toString(), JSON);
        }

        public DirectionsPost call(ApiCallback cb) {
            DirectionsPost directionsPost = new DirectionsPost();

            HttpUtil httpUtil = new HttpUtil(context);
            httpUtil.post(BASE_URL, buildBody(), new HttpCallback() {
                @Override
                public void onFailure(okhttp3.Response response, Throwable throwable) {
                    try {
                        Log.e(TAG, "Failed to receive a response:\n" + Objects.requireNonNull(response.body()).string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(context, context.getResources().getString(R.string.api_response_fail), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(okhttp3.Response response) throws IOException, JSONException {
                    String body = response.body().string();
                    Log.d(TAG, "Response received: " + body);
                    Log.i(TAG, "Remaining call quota: " + response.header("x-ratelimit-remaining", "error"));

                    directionsPost.initialise(new JSONObject(body));
                    cb.onInitialised(directionsPost);
                }
            });
            return directionsPost;
        }
    }
}
