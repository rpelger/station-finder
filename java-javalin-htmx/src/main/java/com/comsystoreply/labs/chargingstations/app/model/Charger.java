package com.comsystoreply.labs.chargingstations.app.model;

import java.math.BigDecimal;

public record Charger(
        String type,
        BigDecimal power,
        String publicKey
) {
}
