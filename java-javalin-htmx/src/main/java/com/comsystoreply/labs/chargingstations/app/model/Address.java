package com.comsystoreply.labs.chargingstations.app.model;

public record Address(
        String street,
        String houseNumber,
        String zipCode,
        String city,
        String province,
        String state) {
}
