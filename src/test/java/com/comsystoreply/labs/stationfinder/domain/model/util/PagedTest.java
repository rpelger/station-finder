package com.comsystoreply.labs.stationfinder.domain.model.util;

import static java.util.List.*;
import static org.hamcrest.MatcherAssert.*;
import org.hamcrest.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

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