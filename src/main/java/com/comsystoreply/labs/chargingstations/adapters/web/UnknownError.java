package com.comsystoreply.labs.chargingstations.adapters.web;

import io.javalin.http.Context;

public class UnknownError extends RuntimeException {
    public UnknownError(Context ctx) {
        super(String.format("Unknown error: %s", ctx.path()));
    }
}
