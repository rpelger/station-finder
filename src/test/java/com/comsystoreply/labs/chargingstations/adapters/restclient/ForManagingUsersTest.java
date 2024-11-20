package com.comsystoreply.labs.chargingstations.adapters.restclient;

import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryStationRepository;
import com.comsystoreply.labs.chargingstations.adapters.db.InMemoryUserRepository;
import com.comsystoreply.labs.chargingstations.app.ChargingStationsApp;
import com.comsystoreply.labs.chargingstations.app.model.UserCredentials;
import com.comsystoreply.labs.chargingstations.app.model.UserRegistration;
import com.comsystoreply.labs.chargingstations.app.usecases.AuthenticateUser;
import com.comsystoreply.labs.chargingstations.app.usecases.RegisterUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.fail;

public class ForManagingUsersTest {
    private ChargingStationsApp app;

    @BeforeEach
    void setup() {
        app = new ChargingStationsApp(
                new BnaCsvStationsRestClient(),
                new InMemoryStationRepository(),
                new InMemoryUserRepository()
        );
    }

    @Test
    void should_register_new_user_as_consumer() {
        var user = app.regigsterNewUser(
                new UserRegistration(
                        new UserCredentials("john@test.de", "test1234"),
                        "John", "Doe"
                ));

        assertThat(user.id(), is(not(nullValue())));
        assertThat(user.isAdmin(), is(false));
        assertThat(user.isConsumer(), is(true));
    }

    @Test
    void should_conflict_when_user_with_same_email_exists() {
        String email = "john@test.de";
        app.regigsterNewUser(new UserRegistration(new UserCredentials(email, "a"),"John", "Doe"));
        try {
            app.regigsterNewUser(new UserRegistration(new UserCredentials(email, "b"), "Jane", "Fox"));
            fail("Should have thrown here");
        } catch (Exception e) {
            assertThat(e , instanceOf(RegisterUser.AlreadyExists.class));
        }
    }

    @Test
    void should_authenticate_existing_user_with_valid_credentials() {
        app.regigsterNewUser(new UserRegistration(new UserCredentials("john@example.com", "test1234"),"John", "Doe"));
        var user = app.authenticateUser(new UserCredentials("john@example.com", "test1234"));

        assertThat(user, is(not(nullValue())));
        assertThat(user.email(), is("john@example.com"));
    }

    @Test
    void should_error_when_authenticating_with_incorrect_password() {
        app.regigsterNewUser(new UserRegistration(new UserCredentials("john@example.com", "test1234"),"John", "Doe"));
        try {
            app.authenticateUser(new UserCredentials("john@example.com", "test6666"));
        } catch(Exception e ){
            assertThat(e , instanceOf(AuthenticateUser.BadCredentials.class));
        }
    }

    @Test
    void should_error_when_authenticating_with_unknown_email() {
        app.regigsterNewUser(new UserRegistration(new UserCredentials("john@example.com", "test1234"),"John", "Doe"));
        try {
            app.authenticateUser(new UserCredentials("hugo@example.com", "test1234"));
        } catch(Exception e ){
            assertThat(e , instanceOf(AuthenticateUser.BadCredentials.class));
        }
    }
}
