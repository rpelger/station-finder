package com.comsystoreply.labs.stationfinder.domain.usecases;

import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.util.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

public class FindNearestStations implements UseCase {
    private final ForStoringStations stationRepo;

    public FindNearestStations(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public Paged<Station> apply(Area area, PageRequest<Station> pageRequest) {
        var stations = stationRepo.findInArea(area, pageRequest);
        var total = stationRepo.countInArea(area);
        return new Paged<>(
                stations,
                pageRequest.limit(),
                total,
                pageRequest.currentPage()
        );

    }
}
