package com.comsystoreply.labs.chargingstations.app.model;

import java.util.List;

public record Station(
        StationId id,
        String operator,
        Location location,
        String startOfService,
        List<Charger> chargers
) {
    public Station withOperator(String operator) {
        return new Station(
                id,
                operator,
                location,
                startOfService,
                chargers
        );
    }
}
