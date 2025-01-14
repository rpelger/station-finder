package com.comsystoreply.labs.chargingstations.adapters.web;

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

    public void listStations(Context context) {
        //var user = Auth.getUser(context);
        var user = User.ADMIN_USER;
        var geo = new Geo(50.935173, 6.953101);
        var radius = new Radius(10.0d);

        var currentPage = context.queryParamAsClass("page", Integer.class).getOrDefault(1);

        var stationsPage = user.isAdmin()
                ? app.getStationsPage(user, new StationPageRequest(currentPage))
                : app.findNearestStations(user, geo, radius);

        var template = isPartialsRequest(context)
                ? "partials/stationsTable.jte"
                : "pages/list-stations.jte";

        context.render(template, Map.of("stationsPage", stationsPage));
    }

    private boolean isPartialsRequest(Context context) {
        return context.header("HX-Target") != null;
    }

}
