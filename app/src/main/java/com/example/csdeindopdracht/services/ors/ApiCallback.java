package com.example.csdeindopdracht.services.ors;

public interface ApiCallback {
    /**
     * Call when Api object is initialised with the query's response.
     */
    public void onInitialised(DirectionsPost apiResponse);
}
