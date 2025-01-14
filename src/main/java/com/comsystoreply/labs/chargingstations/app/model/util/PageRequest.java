package com.comsystoreply.labs.chargingstations.app.model.util;

import java.util.*;

public interface PageRequest<T> {

    Comparator<T> comparator();

    Integer currentPage();

    Integer limit();

    Integer offset();
}
