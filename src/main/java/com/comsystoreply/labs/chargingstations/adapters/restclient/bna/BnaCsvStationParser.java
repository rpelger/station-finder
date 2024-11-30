package com.comsystoreply.labs.chargingstations.adapters.restclient.bna;

import com.comsystoreply.labs.chargingstations.app.model.*;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class BnaCsvStationParser {
    public List<ChargingStation> parse(String csvString) {
        return csvString.lines()
                .skip(11)
                .filter(line -> !line.contains("\""))
                .map(line -> line.split(";"))
                .filter(records -> records.length >= 16)
                .map(record -> {
                    var operator = record[0];
                    var address = new Address(
                            record[1],
                            record[2],
                            record[4],
                            record[5],
                            record[7],
                            record[6]
                    );

                    var geo = new Geo(
                            Double.parseDouble(record[8].replace(".", "").replace(",", ".").replaceAll("[^\\d\\.]", "").trim()),
                            Double.parseDouble(record[9].replace(".", "").replace(",", ".").replaceAll("[^\\d\\.]", "").trim())
                    );
                    var location = new Location(geo, address);
                    var startOfService = record[10];
                    return new ChargingStation(
                            new StationId(DigestUtils.sha1Hex(operator + "/" + location)),
                            operator,
                            location,
                            startOfService,
                            extractChargers(record)
                    );
                })
                .collect(toMap(ChargingStation::id, Function.identity(), this::merge))
                .values()
                .stream()
                .sorted(getComparator())
                .toList();
    }

    private static Comparator<ChargingStation> getComparator() {
        return Comparator
                .<ChargingStation, String>comparing(station -> station.location().address().zipCode())
                .thenComparing(station -> station.location().address().street());

    }

    private List<Charger> extractChargers(String[] record) {
        int amountOfCargers = Integer.parseInt(record[13]);
        var chargers = new ArrayList<Charger>();
        for (int i = 0; i < amountOfCargers * 3; i = i + 3) {
            chargers.add(new Charger(record[14 + i], getPower(record[15 + i])));
        }
        return chargers;
    }

    private static BigDecimal getPower(String value) {
        try {
            return BigDecimal.valueOf(Double.parseDouble(value.replace(",", ".")));
        } catch (Exception e) {
            return BigDecimal.valueOf(-1);
        }
    }

    private ChargingStation merge(ChargingStation chargingStation, ChargingStation chargingStation1) {
        return new ChargingStation(
                chargingStation.id(),
                chargingStation.operator(),
                chargingStation.location(),
                chargingStation.startOfService(),
                Stream.concat(chargingStation.chargers().stream(), chargingStation1.chargers().stream()).toList()
        );
    }


}