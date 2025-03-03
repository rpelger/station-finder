package com.comsystoreply.labs.stationfinder.adapters.web;

import com.comsystoreply.labs.stationfinder.*;
import com.comsystoreply.labs.stationfinder.adapters.web.error.UnknownError;
import com.comsystoreply.labs.stationfinder.adapters.web.error.*;
import com.comsystoreply.labs.stationfinder.domain.*;
import com.comsystoreply.labs.stationfinder.domain.model.error.*;
import gg.jte.ContentType;
import gg.jte.*;
import gg.jte.resolve.*;
import io.javalin.*;
import io.javalin.config.*;
import io.javalin.http.*;
import io.javalin.http.staticfiles.*;
import static io.javalin.http.util.NaiveRateLimit.*;
import io.javalin.rendering.template.*;
import static java.util.concurrent.TimeUnit.*;

import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.function.*;

public class JavalinWebApp {

    private final Javalin webapp;

    public JavalinWebApp(StationFinderApp app) {
        this(app, Env.DEV);
    }

    public JavalinWebApp(StationFinderApp app, Env env) {
        Consumer<JavalinConfig> config = cfg -> {
            cfg.fileRenderer(new JavalinJte(createTemplateEngine(env)));
            cfg.http.defaultContentType = "text/html; charset=utf-8";
            cfg.staticFiles.add("src/main/assets/", Location.EXTERNAL);
        };


        var authHandler = new UserJsonApiHandler(app);
        var stationViewHandler = new StationHtmlViewHandler(app);

        webapp = Javalin.create(config)
                .before("/*", ctx -> requestPerTimeUnit(ctx, 5, SECONDS))

                .post("/auth/registrations", authHandler::registerNewUser)
                .post("/auth/authentications", authHandler::authenticateUser)

                .get("/", context -> context.render("stations/consumer/init_location.jte"))
                .get("/app/stations/near/{lat}/{lng}", stationViewHandler::viewNearestStations)

                .get("/app/stations", stationViewHandler::viewAllStations)
                .get("/app/stations/{id}", stationViewHandler::viewStation)


                .error(404, (ctx) -> ctx.status(404).json(error(404, new InvalidRoute(ctx), ctx)))
                .error(500, (ctx) -> ctx.status(500).json(error(500, new UnknownError(ctx), ctx)))

                .exception(Unauthorized.class, (ex, ctx) -> ctx.status(403).json(error(403, ex, ctx)))
                .exception(UserAlreadyExists.class, (ex, ctx) -> ctx.status(409).json(error(409, ex, ctx)))
                .exception(BadCredentials.class, (ex, ctx) -> ctx.status(401).json(error(401, ex, ctx)))
                .exception(InvalidStationId.class, (ex, ctx) -> ctx.status(400).json(error(400, ex, ctx)))
                .exception(InvalidRequestParam.class, (ex, ctx) -> ctx.status(400).json(error(400, ex, ctx)))
        ;
    }

    private static TemplateEngine createTemplateEngine(Env env) {
        Path sourceDir = Path.of("src/main/jte");
        Path targetDir = Path.of("build/jte-classes");

        // use pre-compiled templates on prod for efficiency
        if (env == Env.PROD) {
            return TemplateEngine.createPrecompiled(
                    targetDir,
                    ContentType.Html);
            // but use on-demand compiled templates on dev for hot-reloading
        } else {
            return TemplateEngine.create(
                    new DirectoryCodeResolver(sourceDir),
                    targetDir,
                    ContentType.Html);
        }
    }

    private static ErrorResponse error(int status, Exception exception, Context context) {
        var endpoint = context.handlerType() == HandlerType.BEFORE
                ? context.method().name() + " " + context.path()
                : context.method().name() + " " + context.endpointHandlerPath();
        return new ErrorResponse(status, endpoint, exception.getMessage());
    }

    public void start(int port) {
        this.webapp.start(port);
    }

    record ErrorResponse(int status, String message, String endpoint, String error, String timestamp) {
        public ErrorResponse(int status, String endpoint, String errorMsg) {
            this(
                    status,
                    HttpStatus.forStatus(status).getMessage(),
                    endpoint,
                    errorMsg,
                    LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        }
    }
}
