package com.comsystoreply.labs.chargingstations.adapters.bna;

import com.comsystoreply.labs.chargingstations.app.model.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class BnaCsvStationParser {
    private static Comparator<Station> getComparator() {
        return Comparator
                .<Station, String>comparing(station -> station.location().address().zipCode())
                .thenComparing(station -> station.location().address().street());

    }

    private static BigDecimal getPower(String value) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(value.replace(",", ".")));
        } catch (Exception e) {
            return BigDecimal.valueOf(-1);
        }
    }

    public List<Station> parse(String csvString) {
        return csvString.lines()
                .skip(11)
                .filter(line -> !line.contains("\""))
                .map(line -> line.split(";"))
                .filter(records -> records.length >= 16)
                .map(record -> {
                    try {
                        return parseLine(record);
                    } catch (Exception e) {
                        System.out.println("Error parsing CSV line: " + String.join(";", record) + "\n" + e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(toMap(Station::id, Function.identity(), this::merge))
                .values()
                .stream()
                .sorted(getComparator())
                .toList();
    }

    private Station parseLine(String[] record) {
        var operator = record[0];
        var address = new Address(
                record[2],
                record[3],
                record[5],
                record[6],
                record[7],
                record[8]
        );

        var geo = new Geo(
                Double.parseDouble(record[9].replace(".", "").replace(",", ".").replaceAll("[^\\d\\.]", "").trim()),
                Double.parseDouble(record[10].replace(".", "").replace(",", ".").replaceAll("[^\\d\\.]", "").trim())
        );
        var location = new Location(geo, address);
        var startOfService = record[11];
        return new Station(
                new StationId(DigestUtils.sha1Hex(operator + "/" + location)),
                operator,
                location,
                startOfService,
                extractChargers(record)
        );
    }

    private List<Charger> extractChargers(String[] record) {
        int amountOfCargers = Integer.parseInt(record[14]);
        var chargers = new ArrayList<Charger>();
        for (int i = 0; i < amountOfCargers * 3; i = i + 3) {
            chargers.add(new Charger(record[15 + i], getPower(record[16 + i])));
        }
        return chargers;
    }

    private Station merge(Station station, Station station1) {
        return new Station(
                station.id(),
                station.operator(),
                station.location(),
                station.startOfService(),
                Stream.concat(station.chargers().stream(), station1.chargers().stream()).toList()
        );
    }


}