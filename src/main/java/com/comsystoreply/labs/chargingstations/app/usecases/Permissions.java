package com.comsystoreply.labs.chargingstations.app.usecases;

import com.comsystoreply.labs.chargingstations.app.model.error.Unauthorized;

import java.util.function.Supplier;

public class Permissions {

    public static void checkAllowed(Supplier<Boolean> condition) {
        if (!condition.get()) {
            var useCase = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
            throw new Unauthorized(useCase);
        }
    }
}
