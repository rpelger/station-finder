package com.comsystoreply.labs.stationfinder.domain.model;

public record Area(
        Geo center,
        Radius radius
) {

    public boolean contains(Geo coords) {
        return new Geo.Distance(center, coords).inKm() <= radius.km();
    }
}
