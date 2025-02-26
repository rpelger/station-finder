package com.comsystoreply.labs.stationfinder.adapters.web;

import com.comsystoreply.labs.stationfinder.adapters.web.error.*;
import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.util.*;
import io.javalin.http.*;

import java.util.*;

public class StationHtmlViewHandler {
    private final StationFinderApp app;

    public StationHtmlViewHandler(StationFinderApp app) {
        this.app = app;
    }

    private static boolean isPartialsRequest(Context context) {
        return context.header("HX-Target") != null;
    }

    public void viewNearestStations(Context context) {
        var rad = context.queryParamAsClass("km", Double.class).getOrDefault(Radius.DEFAULT_RADIUS_MIN_KM);
        var lat = context.pathParamAsClass("lat", Double.class).getOrThrow(InvalidRequestParam::new);
        var lng = context.pathParamAsClass("lng", Double.class).getOrThrow(InvalidRequestParam::new);

        var area = new Area(new Geo(lat, lng), new Radius(rad));
        var sorting = Geo.byDistanceFrom(area.center());
        var pageRequest = new StationPageRequest(sorting);

        var stationsPage = app.findStationsInAreaPaged(area, pageRequest);

        var template = isPartialsRequest(context)
                ? "stations/consumer/fragment_list.jte"
                : "stations/consumer/page.jte";

        context.render(template, Map.of(
                "stationsPage", stationsPage,
                "center", area.center()));
    }

    public void viewAllStations(Context context) {
        var currentPage = context.queryParamAsClass("page", Integer.class).getOrDefault(1);
        var itemsPerPage = context.queryParamAsClass("itemsPerPage", Integer.class).getOrDefault(10);
        var user = User.ADMIN_USER;
        var pageRequest = new StationPageRequest(currentPage, itemsPerPage);

        var stationsPage = app.listAllStationsPaged(user, pageRequest);

        context.render("stations/admin/page.jte", Map.of("stationsPage", stationsPage));
    }

    public void viewStation(Context context) {
        if(isPartialsRequest(context)) {
            viewStationAsConsumer(context);
        } else {
            viewStationAsAdmin(context);
        }
    }

    public void viewStationAsConsumer(Context context) {
        var id = new StationId(context.pathParam("id"));
        var user = User.CONSUMER_USER;
        var station = app.viewStationDetails(user, id);

        context.render("stations/consumer/fragment_detail.jte", Map.of("station", station));
    }

    public void viewStationAsAdmin(Context context) {
        var id = new StationId(context.pathParam("id"));
        var user = User.ADMIN_USER;
        var station = app.viewStationAsAdmin(user, id);

        context.render("station-details/admin/page.jte", Map.of("station", station));
    }
}
