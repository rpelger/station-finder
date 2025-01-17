package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.adapters.web.utils.*;
import com.comsystoreply.labs.chargingstations.app.*;
import com.comsystoreply.labs.chargingstations.app.model.*;
import com.comsystoreply.labs.chargingstations.app.model.util.*;
import io.javalin.http.*;

import java.util.*;

public class StationHtmlViewHandler {
    private final StationFinderApp app;

    public StationHtmlViewHandler(StationFinderApp app) {
        this.app = app;
    }

    public static String toJsonString(Station station) {
        return Json.toJson(new StationResponse(station));
    }

    public void listStations(Context context) {
        //var user = Auth.getUser(context);
        var user = User.ADMIN_USER;
        var geo = new Geo(50.935173, 6.953101);
        var radius = new Radius(10.0d);
        var area = new Area(geo, radius);
        var currentPage = context.queryParamAsClass("page", Integer.class).getOrDefault(1);
        var pageRequest = new StationPageRequest(currentPage);

        var stationsPage = user.isAdmin()
                ? app.getStationsPaged(user, pageRequest)
                : app.findStationsInAreaPaged(user, area, pageRequest);

        var template = isPartialsRequest(context)
                ? "partials/stations_paginated_list.jte"
                : "pages/stations.jte";

        context.render(template, Map.of("stationsPage", stationsPage.mapItems(StationResponse::new)));
    }

    private boolean isPartialsRequest(Context context) {
        return context.header("HX-Target") != null;
    }

}
