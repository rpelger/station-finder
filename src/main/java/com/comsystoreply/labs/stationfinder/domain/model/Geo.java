package com.comsystoreply.labs.stationfinder.domain.model;

import java.util.*;

public record Geo(double lat, double lon) {
    public double distanceFrom(Geo center) {
        return new Distance(this, center).inKm();
    }

    public static Comparator<Station> byDistanceFrom(Geo center) {
        return Comparator.comparing(s -> s.location().geo().distanceFrom(center));
    }

    public record Distance(Geo point1, Geo point2) {
        private static final double EARTH_RADIUS_IN_KM = 6371;

        public double inKm() {
            double lat1Rad = Math.toRadians(point1.lat());
            double lon1Rad = Math.toRadians(point1.lon());
            double lat2Rad = Math.toRadians(point2.lat());
            double lon2Rad = Math.toRadians(point2.lon());

            return Math.acos(
                    (Math.sin(lat1Rad) * Math.sin(lat2Rad)) +
                    (Math.cos(lat1Rad) * Math.cos(lat2Rad)) *
                    (Math.cos(lon2Rad - lon1Rad))
            ) * EARTH_RADIUS_IN_KM;
        }
    }
}
