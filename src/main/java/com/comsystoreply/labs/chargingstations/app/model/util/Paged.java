package com.comsystoreply.labs.chargingstations.app.model.util;

import java.util.*;
import java.util.function.*;

public class Paged<T> {
    private final List<T> items;
    private final int maxItemsOnPage;
    private final int itemsTotal;
    private final int currentPage;

    public Paged(List<T> items, int maxItemsOnPage, int itemsTotal, int currentPage) {
        this.items = items;
        this.maxItemsOnPage = maxItemsOnPage;
        this.itemsTotal = itemsTotal;
        this.currentPage = currentPage;
    }

    public List<T> items() {
        return items;
    }

    public int maxItemsOnPage() {
        return maxItemsOnPage;
    }

    public int itemsTotal() {
        return itemsTotal;
    }

    public int currentPage() {
        return currentPage;
    }

    public int totalPages() {
        float pagesFloat = Integer.valueOf(itemsTotal).floatValue() / Integer.valueOf(maxItemsOnPage).floatValue();
        return Double.valueOf(Math.ceil(pagesFloat)).intValue();
    }

    public int currentPageStart() {
        return (currentPage - 1) * maxItemsOnPage + 1;
    }

    public int currentPageEnd() {
        return currentPage * maxItemsOnPage;
    }

    public <R> Paged<R> mapItems(Function<T, R> mapper) {
        return new Paged<R>(
                this.items.stream().map(mapper).toList(),
                this.maxItemsOnPage,
                this.itemsTotal,
                this.currentPage
        );
    }
}
