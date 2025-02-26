package com.comsystoreply.labs.stationfinder.domain.model.util;

import com.comsystoreply.labs.stationfinder.domain.model.*;

import java.util.*;

public class StationPageRequest implements PageRequest<Station> {

    private static final Comparator<Station> defaultComparator = Comparator.comparing(s -> s);
    private final Comparator<Station> comparator;
    private final int currentPage;
    private final int limit;

    public StationPageRequest() {
        this(1);
    }

    public StationPageRequest(int currentPage) {
        this(currentPage, Integer.MAX_VALUE);
    }

    public StationPageRequest(int currentPage, int limit) {
        this(currentPage, limit, defaultComparator);
    }

    public StationPageRequest(Comparator<Station> comparator) {
        this(1, Integer.MAX_VALUE, comparator);
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
