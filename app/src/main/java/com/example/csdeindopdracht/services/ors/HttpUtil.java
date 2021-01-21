package com.example.csdeindopdracht.services.ors;

import android.content.Context;
import android.os.Handler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    private final String TAG = getClass().getName();
    private final String API_KEY = "5b3ce3597851110001cf62487411bf9180244737b62cc916beed2d7b"; // Key of Dennis
    private final int MAX_URL_LEN = 2048;

    private OkHttpClient client;
    private Request.Builder builder;
    private final Context context;

    public HttpUtil(Context context) {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .readTimeout(3000, TimeUnit.MILLISECONDS)
                .writeTimeout(3000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();
        this.builder = new Request.Builder();
        this.context = context;
    }

    public void get(String url, HttpCallback cb) {
        call(url, null, cb);
    }

    public void post(String url, RequestBody body, HttpCallback cb) {
        call(url, body, cb);
    }

    private void call(String url, RequestBody body, final HttpCallback cb) {
        Request request = body == null ?
                builder.url(url).get().build() :
                builder.url(url)
                        .addHeader("Authorization", API_KEY)
                        .addHeader("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                        .addHeader("Content-Type", "application/json; charset=utf-8")
                        .post(body).build();

        client.newCall(request).enqueue(new Callback() {
            final Handler mainHandler = new Handler(context.getMainLooper());

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                mainHandler.post(() -> {
                    if (!response.isSuccessful()) {
                        cb.onFailure(response, null);
                        return;
                    }
                    try {
                        cb.onSuccess(response);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mainHandler.post(() -> cb.onFailure(null, (Throwable) e));
            }
        });
    }
}


