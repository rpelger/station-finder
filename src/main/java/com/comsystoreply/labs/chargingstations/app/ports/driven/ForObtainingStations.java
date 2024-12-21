package com.comsystoreply.labs.chargingstations.app.ports.driven;

import com.comsystoreply.labs.chargingstations.app.model.Station;

import java.util.List;

public interface ForObtainingStations {
    List<Station> fetchStations();
}
