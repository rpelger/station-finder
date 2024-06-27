package com.comsystoreply.labs.chargingstations.app.model;

public record ChargingStation(
        String id,
        String operator,
        Location location
) {
}
