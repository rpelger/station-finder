package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.adapters.web.error.UnknownError;
import com.comsystoreply.labs.chargingstations.adapters.web.error.*;
import com.comsystoreply.labs.chargingstations.app.StationFinderApp;
import com.comsystoreply.labs.chargingstations.app.model.error.*;
import io.javalin.Javalin;
import io.javalin.http.*;
import io.javalin.http.util.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class JavalinWebApp {

    private final Javalin webapp;

    public JavalinWebApp(StationFinderApp app) {
        var stationApiHandler = new StationJsonApiHandler(app);
        var authHandler = new UserJsonApiHandler(app);

        this.webapp = Javalin.create()
                .before("/*", ctx -> NaiveRateLimit.requestPerTimeUnit(ctx, 5, TimeUnit.SECONDS))

                .post("/auth/registrations", authHandler::registerNewUser)
                .post("/auth/authentications", authHandler::authenticateUser)

                .get("/api/stations", stationApiHandler::listStations)
                .get("/api/stations/near/{lat}/{long}", stationApiHandler::findNearestStations)
                .get("/api/stations/{id}", stationApiHandler::stationDetails)
                .get("/api/stations/{id}/reviews", stationApiHandler::stationReviews)
                .put("/api/stations/{id}", stationApiHandler::updateStationOperator)
                .post("/api/stations/{id}/reviews", stationApiHandler::addStationReview)
                .delete("/api/stations/{id}/reviews/{reviewId}", stationApiHandler::deleteStationReview)

                .error(404, (ctx) -> ctx.status(404).json(error(404, new InvalidRoute(ctx), ctx)))
                .error(500, (ctx) -> ctx.status(500).json(error(500, new UnknownError(ctx), ctx)))

                .exception(Unauthorized.class, (ex, ctx) -> ctx.status(403).json(error(403, ex, ctx)))
                .exception(UserAlreadyExists.class, (ex, ctx) -> ctx.status(409).json(error(409, ex, ctx)))
                .exception(BadCredentials.class, (ex, ctx) -> ctx.status(401).json(error(401, ex, ctx)))
                .exception(InvalidStationId.class, (ex, ctx) -> ctx.status(400).json(error(400, ex, ctx)))
                .exception(InvalidRequestParam.class, (ex, ctx) -> ctx.status(400).json(error(400, ex, ctx)))
        ;
    }

    private static ErrorResponse error(int status, Exception exception, Context context) {
        var endpoint = context.handlerType() == HandlerType.BEFORE
                ? context.method().name() + " " + context.path()
                : context.method().name() + " " + context.endpointHandlerPath();
        return new ErrorResponse(status, endpoint, exception.getMessage());
    }

    public void start(int port) {
        this.webapp.start(port);
    }

    record ErrorResponse(int status, String message, String endpoint, String error, String timestamp) {
        public ErrorResponse(int status, String endpoint, String errorMsg) {
            this(
                    status,
                    HttpStatus.forStatus(status).getMessage(),
                    endpoint,
                    errorMsg,
                    LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        }
    }
}
