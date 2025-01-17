package com.comsystoreply.labs.chargingstations.adapters.web.utils;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

public class Json {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
