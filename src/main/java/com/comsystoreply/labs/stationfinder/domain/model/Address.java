package com.comsystoreply.labs.stationfinder.domain.model;

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

    public static Comparator<Station> byFullAddress() {
        return Comparator.comparing(s -> s.location().address());
    }
}
