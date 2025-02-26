package com.comsystoreply.labs.stationfinder.adapters.web.error;

import io.javalin.http.*;

public class UnknownError extends RuntimeException {
    public UnknownError(Context ctx) {
        super(String.format("Unknown error: %s", ctx.path()));
    }
}
