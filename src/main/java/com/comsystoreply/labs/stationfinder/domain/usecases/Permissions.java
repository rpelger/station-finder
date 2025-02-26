package com.comsystoreply.labs.stationfinder.domain.usecases;

import com.comsystoreply.labs.stationfinder.domain.model.error.*;

import java.util.function.*;

public class Permissions {

    public static void checkAllowed(Supplier<Boolean> condition) {
        if (!condition.get()) {
            var useCase = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
            throw new Unauthorized(useCase);
        }
    }
}
