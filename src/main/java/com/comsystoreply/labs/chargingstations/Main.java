package com.comsystoreply.labs.chargingstations;

import com.comsystoreply.labs.chargingstations.adapters.bna.BnaCsvStationsFileReader;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryUserRepository;
import com.comsystoreply.labs.chargingstations.adapters.job.ImportStationsJob;
import com.comsystoreply.labs.chargingstations.adapters.web.JavalinWebApp;
import com.comsystoreply.labs.chargingstations.app.StationFinderApp;
import com.comsystoreply.labs.chargingstations.app.model.User;

public class Main {

    public static void main(String[] args) {
        // Configurator
        var app = new StationFinderApp(
                new BnaCsvStationsFileReader("/Ladesaeulenregister_BNetzA_2024-09-01_v2.csv"),
                new InMemoryStationRepository(),
                new InMemoryUserRepository()
        );

        // Driving Adapters
        var importStationsJob = new ImportStationsJob(app, User.SYSTEM_USER);
        var webApp = new JavalinWebApp(app);

        importStationsJob.runOnceImmediately();
        webApp.start(8080);
    }

}
