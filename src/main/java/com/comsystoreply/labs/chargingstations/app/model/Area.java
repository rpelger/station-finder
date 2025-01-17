package com.comsystoreply.labs.chargingstations.app.model;

public record Area(
        Geo center,
        Radius radius
) {

    public boolean contains(Geo geo) {
        return true;
    }
}
