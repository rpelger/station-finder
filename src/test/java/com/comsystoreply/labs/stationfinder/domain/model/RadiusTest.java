package com.comsystoreply.labs.stationfinder.domain.model;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;

class RadiusTest {
    @Test
    void should_be_set_to_1_for_negative_values() {
        assertThat(new Radius(-1d), is(new Radius(Radius.DEFAULT_RADIUS_MIN_KM)));
    }

    @Test
    void should_be_capped_at_20() {
        assertThat(new Radius(21d), is(new Radius(Radius.DEFAULT_RADIUS_MAX_KM)));
    }
}