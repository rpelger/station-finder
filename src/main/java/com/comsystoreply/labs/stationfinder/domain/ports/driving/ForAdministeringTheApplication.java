package com.comsystoreply.labs.stationfinder.domain.ports.driving;

import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.util.*;

public interface ForAdministeringTheApplication {
    Paged<Station> listAllStationsPaged(User.Admin systemUser, PageRequest<Station> pageRequest);

    Station viewStationAsAdmin(User.Admin systemUser, StationId stationId);

    void importCurrentStations(User.Admin admin);

    void updateStationOperator(User.Admin systemUser, StationId stationId, String operator);
}
