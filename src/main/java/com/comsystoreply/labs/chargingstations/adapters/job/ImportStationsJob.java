package com.comsystoreply.labs.chargingstations.adapters.job;

import com.comsystoreply.labs.chargingstations.app.model.User;
import com.comsystoreply.labs.chargingstations.app.ports.driving.ForMaintainingStations;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImportStationsJob {
    private final ForMaintainingStations port;
    private final User user;
    private final ExecutorService executor;

    public ImportStationsJob(ForMaintainingStations port, User user) {
        this.port = port;
        this.user = user;
        this.executor = Executors.newFixedThreadPool(1);
    }

    public void runOnceImmediately() {
        executor.submit(() -> port.importCurrentStations(user));
    }
}
