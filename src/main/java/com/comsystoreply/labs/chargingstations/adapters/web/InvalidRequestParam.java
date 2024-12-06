package com.comsystoreply.labs.chargingstations.adapters.web;

import io.javalin.validation.ValidationError;

import java.util.List;
import java.util.Map;

public class InvalidRequestParam extends RuntimeException {
    public InvalidRequestParam(Map<String, ? extends List<ValidationError<Object>>> errors) {
        super(String.format(
                "Invalid request parameter for '%s': %s. Provided value: '%s'",
                errors.entrySet().iterator().next().getKey(),
                errors.entrySet().iterator().next().getValue().getFirst().getMessage(),
                errors.entrySet().iterator().next().getValue().getFirst().getValue()
        ));
    }
}
