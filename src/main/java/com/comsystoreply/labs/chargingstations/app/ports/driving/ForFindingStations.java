package com.comsystoreply.labs.chargingstations.app.ports.driving;

import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.util.*;

public interface ForFindingStations {

    Paged<Station> findStationsInAreaPaged(User user, Area area, PageRequest<Station> pageRequest);

    Station viewStationDetails(User user, StationId stationId);
}
