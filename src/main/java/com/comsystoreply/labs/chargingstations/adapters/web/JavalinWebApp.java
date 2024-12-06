package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.app.StationFinderApp;
import com.comsystoreply.labs.chargingstations.app.model.error.*;
import io.javalin.Javalin;
import io.javalin.http.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavalinWebApp {

    private final Javalin webapp;

    public JavalinWebApp(StationFinderApp app) {
        var stationRequestHandler = new StationRequestHandler(app);
        var userRequestHandler = new UserRequestHandler(app);

        // @formatter:off
        this.webapp = Javalin.create()
                .get("/stations", stationRequestHandler::listStations)
                .get ("/stations/near/{lat}/{long}", stationRequestHandler::findNearestStations)
                .get ("/stations/{id}", stationRequestHandler::stationDetails)
                .get ("/stations/{id}/reviews", stationRequestHandler::stationReviews)
                .put ("/stations/{id}", stationRequestHandler::updateStationOperator)
                .post("/stations/{id}/reviews", stationRequestHandler::addStationReview)

                .post("/auth/registrations", userRequestHandler::registerNewUser)
                .post("/auth/authentications", userRequestHandler::authenticateUser)

                .error(404, (ctx) -> ctx.status(404).json(error(404, new InvalidRoute(ctx), ctx)))
                .error(500, (ctx) -> ctx.status(500).json(error(500, new UnknownError(ctx), ctx)))

                .exception(Unauthorized.class,        (ex, ctx) -> ctx.status(403).json(error(403, ex, ctx)))
                .exception(UserAlreadyExists.class,   (ex, ctx) -> ctx.status(409).json(error(409, ex, ctx)))
                .exception(BadCredentials.class,      (ex, ctx) -> ctx.status(401).json(error(401, ex, ctx)))
                .exception(InvalidStationId.class,    (ex, ctx) -> ctx.status(400).json(error(400, ex, ctx)))
                .exception(InvalidRequestParam.class, (ex, ctx) -> ctx.status(400).json(error(400, ex, ctx)))
        ;
        // @formatter:on
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
