package com.comsystoreply.labs.chargingstations.app.model.util;

import org.hamcrest.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static java.util.List.*;
import static org.hamcrest.MatcherAssert.*;

class PagedTest {
    @ParameterizedTest
    @CsvSource({
            "10, 11,  2",
            "10, 99, 10",
            " 3, 10,  4",
            "15, 45,  3"
    })
    void should_return_total_pages_correctly(int maxItemsOnPage, int itemsTotal, int expectedTotalPages) {
        assertThat(new Paged<>(of(), maxItemsOnPage, itemsTotal, 0).totalPages(), Matchers.is(expectedTotalPages));
    }
}