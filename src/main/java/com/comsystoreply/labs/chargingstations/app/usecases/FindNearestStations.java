package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.*;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.util.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.*;

public class FindNearestStations implements UseCase {
    private final ForStoringStations stationRepo;

    public FindNearestStations(ForStoringStations stationRepo) {
        this.stationRepo = stationRepo;
    }

    public Paged<Station> apply(User user, Area area, PageRequest<Station> pageRequest) {
        Permissions.checkAllowed(() -> user.isConsumer() || user.isGuest());
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
