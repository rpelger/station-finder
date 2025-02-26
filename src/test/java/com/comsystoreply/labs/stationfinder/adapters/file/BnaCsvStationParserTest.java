package com.comsystoreply.labs.stationfinder.adapters.file;

import com.comsystoreply.labs.stationfinder.domain.model.*;
import org.apache.commons.codec.digest.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;

class BnaCsvStationParserTest {

    public static final String TEST_CSV = """
            Line1
            Line2
            Line3
            Line4
            Line5
            Line6
            Line7
            Line8
            Line9
            Line10
            Line11
            Operator-1;_ignored_;Straße-A;10a;_ignored_;72535;Stadt-1;Landkreis-1;Bundesland-1;48,442398;9,659075;11.01.2020;_ignored_;_ignored_;2;AC Typ 2 Steckdose;22;_ignored_;AC Typ 2 Steckdose;22;_rest_;_is_;_also_;_ignored_;;;;;;;;;
            this;line;is;skipped;less;than;16;columns
            Operator-2;_ignored_;Straße-B;20b;_ignored_;92345;Stadt-2;Landkreis-2;Bundesland-2;39,234567;8,987654;31.07.2023;_ignored_;_ignored_;1;DC Kupplung;110;_ignored_;;;_rest_;_is_;_also_;_ignored_;;;;;;;;;
            Operator-3;_ignored_;Straße-C;30b;_ignored_;82345;Stadt-3;Landkreis-3;Bundesland-3;39,234567;8,987654;31.07.2023;_ignored_;_ignored_;1;DC Kupplung;superpower;_ignored_;;;_rest_;_is_;_also_;_ignored_;;;;;;;;;
            Operator-error;_ignored_;Str.;40b;_ignored_;99945;Stadt-4;Landkreis-4;Bundesland-4;invalid-latitude;8,987654;31.07.2023;_ignored_;_ignored_;1;DC Kupplung;110;_ignored_;;;_rest_;_is_;_also_;_ignored_;;;;;;;;;
            """;

    private BnaCsvStationParser parser;

    @BeforeEach
    void setup() {
        parser = new BnaCsvStationParser();
    }

    @Test
    void should_parse_valid_content() {
        List<Station> stations = parser.parse(TEST_CSV);

        // expected values
        var operator = "Operator-1";
        var location = new Location(
                new Geo(48.442398d, 9.659075d),
                new Address("Straße-A", "10a", "72535", "Stadt-1", "Landkreis-1", "Bundesland-1"));
        var id = new StationId(DigestUtils.sha1Hex(operator + "/" + location));
        var startOfService = "11.01.2020";
        var chargers = List.of(
                new Charger("AC Typ 2 Steckdose", BigDecimal.valueOf(22.0d)),
                new Charger("AC Typ 2 Steckdose", BigDecimal.valueOf(22.0d)));

        assertThat(stations, hasSize(3));
        assertThat(
                stations.get(0),
                is(new Station(id, operator, location, startOfService, chargers)));
        assertThat(stations.get(0).id(), not(equalTo(stations.get(1).id())));
        assertThat(stations.get(0).id(), not(equalTo(stations.get(2).id())));
        assertThat(stations.get(1).id(), not(equalTo(stations.get(2).id())));
    }

    @Test
    void should_sort_stations_by_zipcode() {
        var stations = parser.parse(TEST_CSV);

        assertThat(stations.get(0).operator(), is("Operator-1"));
        assertThat(stations.get(1).operator(), is("Operator-3"));
        assertThat(stations.get(2).operator(), is("Operator-2"));

        assertThat(stations.get(0).location().address().zipCode(), is("72535"));
        assertThat(stations.get(1).location().address().zipCode(), is("82345"));
        assertThat(stations.get(2).location().address().zipCode(), is("92345"));
    }

    @Test
    void should_have_power_minus1_when_unparsable_value() {
        var stations = parser.parse(TEST_CSV);

        assertThat(stations.get(1).operator(), is("Operator-3"));
        assertThat(stations.get(1).chargers().get(0).power(), is(BigDecimal.valueOf(-1L)));

    }
}