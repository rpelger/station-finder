package com.comsystoreply.labs.stationfinder.adapters.web.error;

import io.javalin.http.*;

public class InvalidRoute extends RuntimeException {
    public InvalidRoute(Context ctx) {
        super(String.format("The requested route '%s' was not found.", ctx.path()));
    }
}
