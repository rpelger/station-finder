package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.app.model.*;

import java.util.*;

import static java.util.stream.Collectors.*;

public record StationResponse(
        String id,
        String operator,
        String startOfServiceDate,
        Location location,
        List<ChargerResponse> chargers) {
    public StationResponse(Station station) {
        this(station.id().value(),
                station.operator(),
                station.startOfService(),
                station.location(),
                mergeIntoResponse(station.chargers()));
    }

    public static List<ChargerResponse> mergeIntoResponse(List<Charger> chargers) {
        return chargers.stream()
                .collect(groupingBy(charger -> String.format("%s (%s kW)", charger.type(), charger.power())))
                .entrySet()
                .stream()
                .map(entry -> new ChargerResponse(entry.getKey(), entry.getValue().size()))
                .sorted(Comparator.comparing(ChargerResponse::name))
                .toList();
    }

    public record ChargerResponse(
            String name,
            int count) {
    }
}
