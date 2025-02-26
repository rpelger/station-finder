package com.comsystoreply.labs.stationfinder.domain.model;

import static java.util.stream.Collectors.*;
import org.jetbrains.annotations.*;

import java.util.*;

public record Station(
        StationId id,
        String operator,
        Location location,
        String startOfService,
        List<Charger> chargers
) implements Comparable<Station> {
    public Station withOperator(String operator) {
        return new Station(
                id,
                operator,
                location,
                startOfService,
                chargers
        );
    }

    @Override
    public int compareTo(@NotNull Station o) {
        return this.location.address().compareTo(o.location.address());
    }

    public List<ChargerAggregate> chargersAggregated() {
        return chargers.stream()
                .collect(groupingBy(charger -> String.format("%s (%s kW)", charger.type(), charger.power())))
                .entrySet()
                .stream()
                .map(entry -> new ChargerAggregate(entry.getKey(), entry.getValue().size()))
                .sorted(Comparator.comparing(ChargerAggregate::name))
                .toList();
    }

    public record ChargerAggregate(
            String name,
            int count
    ) {
    }
}
