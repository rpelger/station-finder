package com.comsystoreply.labs.stationfinder.domain.usecases;

import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.util.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driven.*;

public class ListAllStationsPaged implements UseCase {
    private final ForStoringStations stationsRepo;

    public ListAllStationsPaged(ForStoringStations stationsRepo) {
        this.stationsRepo = stationsRepo;
    }

    public Paged<Station> apply(User.Admin admin, PageRequest<Station> pageRequest) {
        var stations = stationsRepo.getStations(pageRequest);
        var totalItems = stationsRepo.count();

        return new Paged<>(
                stations,
                pageRequest.limit(),
                totalItems,
                pageRequest.currentPage());
    }
}
