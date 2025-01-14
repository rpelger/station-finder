package com.comsystoreply.labs.chargingstations.app.model.util;

import com.comsystoreply.labs.chargingstations.app.model.*;

import java.util.*;

public class StationPageRequest implements PageRequest<Station> {
    private final Comparator<Station> comparator;
    private final int currentPage;
    private final int limit;

    public StationPageRequest() {
        this(1);
    }

    public StationPageRequest(int currentPage) {
        this(currentPage, 10);
    }

    public StationPageRequest(int currentPage, int limit) {
        this(currentPage, limit, Comparator.comparing(s -> s.location().address()));
    }

    public StationPageRequest(int currentPage, int limit, Comparator<Station> comparator) {
        this.currentPage = currentPage;
        this.limit = limit;
        this.comparator = comparator;
    }

    @Override
    public Comparator<Station> comparator() {
        return comparator;
    }

    @Override
    public Integer currentPage() {
        return currentPage;
    }

    @Override
    public Integer limit() {
        return limit;
    }

    @Override
    public Integer offset() {
        return (currentPage - 1) * limit;
    }
}
