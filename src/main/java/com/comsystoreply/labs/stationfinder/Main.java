package com.comsystoreply.labs.stationfinder;

import com.comsystoreply.labs.stationfinder.adapters.db.*;
import com.comsystoreply.labs.stationfinder.adapters.file.*;
import com.comsystoreply.labs.stationfinder.adapters.job.*;
import com.comsystoreply.labs.stationfinder.adapters.web.*;
import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;

public class Main {

    public static void main(String[] args) {
        var env = Env.from(System.getenv("JAVA_ENV"));
        // Configurator
        var app = new StationFinderApp(
                // Driven Adapters
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
