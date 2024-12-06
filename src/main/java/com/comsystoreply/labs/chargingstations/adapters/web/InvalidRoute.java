package com.comsystoreply.labs.chargingstations.adapters.web;

import io.javalin.http.Context;

public class InvalidRoute extends RuntimeException {
    public InvalidRoute(Context ctx) {
        super(String.format("The requested route '%s' was not found.", ctx.path()));
    }
}
