package com.comsystoreply.labs.stationfinder.domain.model;

import com.comsystoreply.labs.stationfinder.domain.model.error.*;
import static org.hamcrest.MatcherAssert.*;
import org.hamcrest.*;
import org.junit.jupiter.api.*;

import java.util.*;

class UserTest {

    private static final User REGULAR_USER = new User(new UserId(1L), "", "", "", "", Set.of(User.Role.REGULAR));
    private static final User ADMIN_USER = new User(new UserId(1L), "", "", "", "", Set.of(User.Role.ADMIN));
    private static final User SYSTEM_USER = new User(new UserId(1L), "", "", "", "", Set.of(User.Role.SYSTEM));
    private static final User ALL_ROLES_USER = new User(new UserId(1L), "", "", "", "", Set.of(User.Role.SYSTEM, User.Role.ADMIN, User.Role.REGULAR));

    @Test
    void should_provide_valid_user_types() {
        assertThat(new User.RegularUser(REGULAR_USER).user, Matchers.is(REGULAR_USER));
        assertThat(new User.Admin(ADMIN_USER).user, Matchers.is(ADMIN_USER));
        assertThat(new User.SystemUser(SYSTEM_USER).user, Matchers.is(SYSTEM_USER));
        assertThat(new User.RegularUser(ALL_ROLES_USER).user, Matchers.is(ALL_ROLES_USER));
        assertThat(new User.Admin(ALL_ROLES_USER).user, Matchers.is(ALL_ROLES_USER));
        assertThat(new User.SystemUser(ALL_ROLES_USER).user, Matchers.is(ALL_ROLES_USER));
    }

    @Test
    void should_throw_if_required_roles_not_present() {
        Assertions.assertThrows(Unauthorized.class, () -> new User.RegularUser(ADMIN_USER));
        Assertions.assertThrows(Unauthorized.class, () -> new User.RegularUser(SYSTEM_USER));
        Assertions.assertThrows(Unauthorized.class, () -> new User.Admin(REGULAR_USER));
        Assertions.assertThrows(Unauthorized.class, () -> new User.Admin(SYSTEM_USER));
        Assertions.assertThrows(Unauthorized.class, () -> new User.SystemUser(REGULAR_USER));
        Assertions.assertThrows(Unauthorized.class, () -> new User.SystemUser(REGULAR_USER));
    }
}