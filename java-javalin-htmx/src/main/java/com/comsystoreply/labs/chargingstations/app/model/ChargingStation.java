package com.comsystoreply.labs.chargingstations.app.model;

import java.util.List;

public record ChargingStation(
        StationId id,
        String operator,
        Location location,
        String startOfService,
        List<Charger> chargers
) {
}
