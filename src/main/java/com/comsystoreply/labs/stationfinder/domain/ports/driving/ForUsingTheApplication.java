package com.comsystoreply.labs.stationfinder.domain.ports.driving;

import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.util.*;

import java.util.*;

public interface ForUsingTheApplication {

    Paged<Station> findStationsInAreaPaged(Area area, PageRequest<Station> pageRequest);

    Station viewStationDetails(User.RegularUser consumer, StationId stationId);

    List<Review> listStationReviews(User.RegularUser consumer, StationId id);

    Review addStationReview(User.RegularUser consumer, StationId stationId, String reviewText);

    void deleteStationReview(User.RegularUser consumer, ReviewId reviewId);
}
