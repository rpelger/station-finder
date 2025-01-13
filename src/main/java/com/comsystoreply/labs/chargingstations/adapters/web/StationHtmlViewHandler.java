package com.comsystoreply.labs.chargingstations.adapters.web;

import com.comsystoreply.labs.chargingstations.app.StationFinderApp;
import com.comsystoreply.labs.chargingstations.app.model.User;
import io.javalin.http.Context;

import java.util.*;

public class StationHtmlViewHandler {
    private final StationFinderApp app;

    public StationHtmlViewHandler(StationFinderApp app) {
        this.app = app;
    }

    public void listStations(Context context) {
        //var user = Auth.getUser(context);
        var user = User.ADMIN_USER;
        if(user.isAdmin()) {
            renderListAllStations(context, user);
        } else {
            renderFindStations(context, user);
        }
    }

    private void renderListAllStations(Context context, User user) {
        var stations = app.listAll(user).stream().limit(10).toList();
        context.render("pages/list-stations.jte", Map.of("stations", stations));
    }

    private void renderFindStations(Context context, User user) {
        context.render("find-stations.jte");
    }
}
