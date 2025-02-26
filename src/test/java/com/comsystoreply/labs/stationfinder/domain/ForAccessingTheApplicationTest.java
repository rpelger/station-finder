package com.comsystoreply.labs.stationfinder.domain;

import com.comsystoreply.labs.stationfinder.adapters.db.*;
import com.comsystoreply.labs.stationfinder.domain.model.*;
import com.comsystoreply.labs.stationfinder.domain.model.error.*;
import com.comsystoreply.labs.stationfinder.domain.ports.driving.*;
import org.hamcrest.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.*;

public class ForAccessingTheApplicationTest {
    private ForAccessingTheApplication port;

    @BeforeEach
    void setup() {
        port = new StationFinderApp(
                List::of,
                new InMemoryStationRepository(),
                new InMemoryUserRepository()
        );
    }

    @Test
    void should_register_new_user_as_consumer() {
        var user = port.regigsterNewUser(
                new UserRegistration(
                        new UserCredentials("john@test.de", "test1234"),
                        "John", "Doe"
                ));

        assertThat(user.id(), is(not(nullValue())));
        MatcherAssert.assertThat(User.withRequiredRole(user, User.Role.REGULAR), is(user));
        Assertions.assertThrows(Unauthorized.class, () -> User.withRequiredRole(user, User.Role.ADMIN));
        Assertions.assertThrows(Unauthorized.class, () -> User.withRequiredRole(user, User.Role.SYSTEM));
    }

    @Test
    void should_conflict_when_user_with_same_email_exists() {
        String email = "john@test.de";
        port.regigsterNewUser(new UserRegistration(new UserCredentials(email, "a"), "John", "Doe"));
        try {
            port.regigsterNewUser(new UserRegistration(new UserCredentials(email, "b"), "Jane", "Fox"));
            fail("Should have thrown here");
        } catch (Exception e) {
            assertThat(e, instanceOf(UserAlreadyExists.class));
        }
    }

    @Test
    void should_authenticate_existing_user_with_valid_credentials() {
        port.regigsterNewUser(new UserRegistration(new UserCredentials("john@example.com", "test1234"), "John", "Doe"));
        var user = port.authenticateUser(new UserCredentials("john@example.com", "test1234"));

        assertThat(user, is(not(nullValue())));
        assertThat(user.email(), is("john@example.com"));
    }

    @Test
    void should_error_when_authenticating_with_incorrect_password() {
        port.regigsterNewUser(new UserRegistration(new UserCredentials("john@example.com", "test1234"), "John", "Doe"));
        try {
            port.authenticateUser(new UserCredentials("john@example.com", "test6666"));
        } catch (Exception e) {
            assertThat(e, instanceOf(BadCredentials.class));
        }
    }

    @Test
    void should_error_when_authenticating_with_unknown_email() {
        port.regigsterNewUser(new UserRegistration(new UserCredentials("john@example.com", "test1234"), "John", "Doe"));
        try {
            port.authenticateUser(new UserCredentials("hugo@example.com", "test1234"));
        } catch (Exception e) {
            assertThat(e, instanceOf(BadCredentials.class));
        }
    }
}
