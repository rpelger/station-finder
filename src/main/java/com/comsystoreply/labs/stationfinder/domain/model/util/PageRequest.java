package com.comsystoreply.labs.stationfinder.domain.model.util;

import java.util.*;

public interface PageRequest<T> {

    Comparator<T> comparator();

    Integer currentPage();

    Integer limit();

    Integer offset();
}
