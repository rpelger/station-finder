package com.comsystoreply.labs.chargingstations.app.model;

import org.jetbrains.annotations.*;

import java.util.*;

public record Address(
        String street,
        String houseNumber,
        String zipCode,
        String city,
        String province,
        String state) implements Comparable<Address> {

    @Override
    public int compareTo(@NotNull Address o) {
        return Comparator
                .comparing(Address::zipCode)
                .thenComparing(Address::street)
                .thenComparing(Address::houseNumber)
                .compare(this, o);
    }
}
