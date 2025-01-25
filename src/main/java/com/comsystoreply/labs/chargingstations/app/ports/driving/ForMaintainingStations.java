package com.comsystoreply.labs.chargingstations.app.ports.driving;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.util.*;

public interface ForMaintainingStations {
    Paged<Station> listAllStationsPaged(User user, PageRequest<Station> pageRequest);

    Station viewStationAsAdmin(User user, StationId stationId);

    void importCurrentStations(User user);

    void updateStationOperator(User user, StationId stationId, String operator);
}
