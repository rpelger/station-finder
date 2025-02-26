package com.comsystoreply.labs.stationfinder.domain.model;

import org.hamcrest.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class AreaTest {
    @Test
    void should_contain_self() {
        var coord = new Geo(48.2981212, 7.0128292);

        assertTrue(new Area(coord, new Radius(0.0d)).contains(coord));
        assertTrue(new Area(coord, new Radius(0.1d)).contains(coord));
        assertTrue(new Area(coord, new Radius(1.0d)).contains(coord));
    }

    @Test
    void should_not_contain_outside_coord() {
        var center = new Geo(48.2981212, 7.0128292);
        var radius = new Radius(1.0d);
        var area = new Area(center, radius);

        Geo point1 = new Geo(10.0d, 20.0d);
        Geo point2 = new Geo(48.2d, 7.0d);
        Geo point3 = new Geo(48.29d, 7.01d);

        MatcherAssert.assertThat(new Geo.Distance(center, point1).inKm(), is(greaterThan(1.0d)));
        MatcherAssert.assertThat(new Geo.Distance(center, point2).inKm(), is(greaterThan(1.0d)));
        MatcherAssert.assertThat(new Geo.Distance(center, point3).inKm(), is(lessThan(1.0d)));
        assertFalse(area.contains(point1));
        assertFalse(area.contains(point2));
        assertTrue(area.contains(point3));
    }
}