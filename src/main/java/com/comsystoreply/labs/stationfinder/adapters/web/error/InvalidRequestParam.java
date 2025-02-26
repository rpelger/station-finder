package com.comsystoreply.labs.stationfinder.adapters.web.error;

import io.javalin.validation.*;

import java.util.*;

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
