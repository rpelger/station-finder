package com.comsystoreply.labs.chargingstations;

import com.comsystoreply.labs.chargingstations.adapters.db.StationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.UserRepository;
import com.comsystoreply.labs.chargingstations.adapters.rest.BnaCsvStationLoader;
import com.comsystoreply.labs.chargingstations.adapters.web.JavalinWebApp;
import com.comsystoreply.labs.chargingstations.app.ChargingStationsApp;

public class Main {

    public static void main(String[] args) {
        var chargingStationsApp = new ChargingStationsApp(
                new BnaCsvStationLoader(),
                new StationRepository(),
                new UserRepository()
        );

        var webapp = new JavalinWebApp(chargingStationsApp);

        chargingStationsApp.refreshChargingStations();
        webapp.start(8080);
    }

}
