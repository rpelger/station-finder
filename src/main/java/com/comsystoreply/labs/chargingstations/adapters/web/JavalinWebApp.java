package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.app.ChargingStationsApp;
import com.comsystoreply.labs.chargingstations.app.Permissions;
import com.comsystoreply.labs.chargingstations.app.usecases.*;
import io.javalin.Javalin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JavalinWebApp {

    private final Javalin webapp;

    public JavalinWebApp(ChargingStationsApp app) {
        var stationRequestHandler = new StationRequestHandler(app);
        var userRequestHandler = new UserRequestHandler(app);

        // @formatter:off
        this.webapp = Javalin.create()
                .get ("/charging-stations/near/{lat}/{long}", stationRequestHandler::findNearestStations)
                .get ("/charging-stations/{id}", stationRequestHandler::stationDetails)
                .get ("/charging-stations/{id}/reviews", stationRequestHandler::stationReviews)
                .put("/charging-stations/{id}", stationRequestHandler::updateStationOperator)
                .post("/charging-stations/{id}/reviews", stationRequestHandler::addStationReview)

                .post("/auth/registrations", userRequestHandler::registerNewUser)
                .post("/auth/authentications", userRequestHandler::authenticateUser)
                .exception(Permissions.Unauthorized.class,
                        (exception, ctx) -> ctx
                                .status(403)
                                .json(new ErrorResponse(403, exception.getMessage())))
                .exception(RegisterUser.AlreadyExists.class,
                        (exception, ctx) -> ctx
                                .status(409)
                                .json(new ErrorResponse(409, exception.getMessage())))
                .exception(AuthenticateUser.BadCredentials.class,
                        (exception, ctx) -> ctx
                                .status(401)
                                .json(new ErrorResponse(401, exception.getMessage())))
                .exception(UpdateStationOperator.InvalidStationId.class,
                        (exception, ctx) -> ctx
                                .status(400)
                                .json(new ErrorResponse(400, exception.getMessage())))
        ;
    }

    record ErrorResponse(int status, String message, String timestamp) {
        public ErrorResponse(int status, String message) {
            this(status, message, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        }
    }

    public void start(int port) {
        this.webapp.start(port);
    }
}
