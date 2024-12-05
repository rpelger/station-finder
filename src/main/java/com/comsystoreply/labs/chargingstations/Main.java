package com.comsystoreply.labs.chargingstations;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryUserRepository;
import com.comsystoreply.labs.chargingstations.adapters.bna.BnaCsvStationsRestClient;
import com.comsystoreply.labs.chargingstations.adapters.web.JavalinWebApp;
import com.comsystoreply.labs.chargingstations.app.StationFinderApp;
import com.comsystoreply.labs.chargingstations.app.model.User;

public class Main {

    public static void main(String[] args) {
        var chargingStationsApp = new StationFinderApp(
                new BnaCsvStationsRestClient(),
                new InMemoryStationRepository(),
                new InMemoryUserRepository().withDummyData()
        );

        var webapp = new JavalinWebApp(chargingStationsApp);

        chargingStationsApp.importCurrentStations(User.SYSTEM_USER);
        webapp.start(8080);
    }

}
