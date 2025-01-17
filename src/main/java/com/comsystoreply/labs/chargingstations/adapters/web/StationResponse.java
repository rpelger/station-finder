package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.app.model.*;

import java.util.*;

public record StationResponse(
        String id,
        String operator,
        String startOfServiceDate,
        Location location,
        List<Charger> chargers) {
    public StationResponse(Station station) {
        this(station.id().value(), station.operator(), station.startOfService(), station.location(), station.chargers());
    }
}
