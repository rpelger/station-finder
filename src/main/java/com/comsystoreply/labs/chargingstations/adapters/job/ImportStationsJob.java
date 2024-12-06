package com.comsystoreply.labs.chargingstations.adapters.job;

import com.comsystoreply.labs.chargingstations.app.StationFinderApp;
import com.comsystoreply.labs.chargingstations.app.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImportStationsJob {
    private final StationFinderApp app;
    private final User user;
    private final ExecutorService executor;

    public ImportStationsJob(StationFinderApp app, User user) {
        this.app = app;
        this.user = user;
        this.executor = Executors.newFixedThreadPool(1);
    }

    public void runOnceImmediately() {
        executor.submit(() -> app.importCurrentStations(user));
    }
}
