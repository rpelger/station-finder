package com.comsystoreply.labs.chargingstations.app.ports.driving;

import com.comsystoreply.labs.chargingstations.app.model.*;

import java.util.List;

public interface ForFindingStations {

    List<Station> findNearestStations(User user, Location location, Radius radius);

    Station viewStationDetails(User user, StationId stationId);
}
