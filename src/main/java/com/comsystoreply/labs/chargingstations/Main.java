package com.comsystoreply.labs.chargingstations;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryUserRepository;
import com.comsystoreply.labs.chargingstations.adapters.restclient.BnaCsvStationsRestClient;
import com.comsystoreply.labs.chargingstations.adapters.web.JavalinWebApp;
import com.comsystoreply.labs.chargingstations.app.ChargingStationsApp;

public class Main {

    public static void main(String[] args) {
        var chargingStationsApp = new ChargingStationsApp(
                new BnaCsvStationsRestClient(),
                new InMemoryStationRepository(),
                new InMemoryUserRepository()
        );

        var webapp = new JavalinWebApp(chargingStationsApp);

        chargingStationsApp.importCurrentStations();
        webapp.start(8080);
    }

}
