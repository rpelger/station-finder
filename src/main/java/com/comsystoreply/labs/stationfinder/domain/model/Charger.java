package com.comsystoreply.labs.stationfinder.domain.model;

import java.math.*;

public record Charger(
        String type,
        BigDecimal power
) {
}
