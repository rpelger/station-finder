package com.comsystoreply.labs.chargingstations.adapters.rest;

import com.comsystoreply.labs.chargingstations.app.ports.driven.ForObtainingStations;
import com.comsystoreply.labs.chargingstations.app.model.Address;
import com.comsystoreply.labs.chargingstations.app.model.ChargingStation;
import com.comsystoreply.labs.chargingstations.app.model.Geo;
import com.comsystoreply.labs.chargingstations.app.model.Location;
import io.badgod.jayreq.Body;
import io.badgod.jayreq.Headers;
import io.badgod.jayreq.JayReq;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Collections;
import java.util.List;

public class BnaCsvStationLoader implements ForObtainingStations {

    @Override
    public List<ChargingStation> loadAllChargingStations() {
        var response = JayReq.get("https://data.bundesnetzagentur.de/Bundesnetzagentur/SharedDocs/Downloads/DE/Sachgebiete/Energie/Unternehmen_Institutionen/E_Mobilitaet/Ladesaeulenregister.csv");

        return response
                .body(new CsvStationParser())
                .orElse(Collections.emptyList());

    }

    private static class CsvStationParser implements Body.Converter<List<ChargingStation>> {
        @Override
        public List<ChargingStation> apply(int status, Headers headers, String body) {
            return body.lines()
                    .skip(11)
                    .filter(line -> !line.contains("\""))
                    .map(line -> line.split(";"))
                    .filter(records -> records.length >= 16)
                    .map(record -> {
                        var operator = record[0];
                        Geo geo = null;
                        geo = new Geo(
                                Double.parseDouble(record[8].replace(",", ".").replaceAll("[^\\d\\.]", "").trim()),
                                Double.parseDouble(record[9].replace(",", ".").replaceAll("[^\\d\\.]", "").trim())
                        );
                        Address address = new Address(
                                record[1],
                                record[2],
                                record[4],
                                record[5],
                                record[7],
                                record[6]
                        );

                        var location = new Location(geo, address);
                        return new ChargingStation(
                                DigestUtils.sha1Hex(operator + "/" + location),
                                operator,
                                location
                        );
                    })
                    .toList();
        }
    }
}
