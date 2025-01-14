package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.*;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.util.*;
import com.comsystoreply.labs.chargingstations.app.ports.driven.*;

public class ListAllStationsPaged implements UseCase {
    private final ForStoringStations stationsRepo;

    public ListAllStationsPaged(ForStoringStations stationsRepo) {
        this.stationsRepo = stationsRepo;
    }

    public Paged<Station> apply(User user, PageRequest<Station> pageRequest) {
        Permissions.checkAllowed(user::isAdmin);

        var stations = stationsRepo.getStations(pageRequest);
        var totalItems = stationsRepo.count();

        return new Paged<>(
                stations,
                pageRequest.limit(),
                totalItems,
                pageRequest.currentPage());
    }
}
