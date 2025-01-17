package com.comsystoreply.labs.chargingstations.adapters.web.utils;

import com.comsystoreply.labs.chargingstations.app.model.User;
import io.javalin.http.Context;

import java.util.Optional;

public class Auth {

    public static User getUser(Context context) {
        return Optional
                .ofNullable(context.<User>sessionAttribute("current_user"))
                .orElse(User.GUEST_USER);
    }

}
