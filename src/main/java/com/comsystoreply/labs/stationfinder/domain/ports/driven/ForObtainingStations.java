package com.comsystoreply.labs.stationfinder.domain.ports.driven;

import com.comsystoreply.labs.stationfinder.domain.model.*;

import java.util.*;

public interface ForObtainingStations {
    List<Station> fetchStations();
}
