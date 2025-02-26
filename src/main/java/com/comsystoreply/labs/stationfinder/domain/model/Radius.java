package com.comsystoreply.labs.stationfinder.domain.model;

public record Radius(double km) {
    public static final Double DEFAULT_RADIUS_MIN_KM = 0.5d;
    public static final Double DEFAULT_RADIUS_MAX_KM = 10.0d;

    public Radius {
        if (km < 0d) {
            km = DEFAULT_RADIUS_MIN_KM;
        }
        if (km > 10d) {
            km = DEFAULT_RADIUS_MAX_KM;
        }
    }
}
