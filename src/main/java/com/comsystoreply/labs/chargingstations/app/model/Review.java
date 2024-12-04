package com.comsystoreply.labs.chargingstations.app.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Review(
        ReviewId id,
        String comment,
        LocalDateTime createdAt,
        UserId createdBy) {

    public Review(User user, String comment) {
        this(new ReviewId(UUID.randomUUID().toString()), comment, LocalDateTime.now(), user.id());
    }
}
