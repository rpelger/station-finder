package com.comsystoreply.labs.stationfinder.domain.model;

import java.time.*;
import java.util.*;

public record Review(
        ReviewId id,
        String comment,
        LocalDateTime createdAt,
        UserId createdBy) {

    public Review(User user, String comment) {
        this(new ReviewId(UUID.randomUUID().toString()), comment, LocalDateTime.now(), user.id());
    }

    public boolean isCreatedBy(User user) {
        return user.id().equals(createdBy);
    }
}
