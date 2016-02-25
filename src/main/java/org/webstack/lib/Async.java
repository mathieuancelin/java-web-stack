package org.webstack.lib;

import ratpack.handling.Context;
import ratpack.http.Response;
import ratpack.rx.RxRatpack;
import rx.Observable;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Async {

    static {
        RxRatpack.initialize();
    }

    public static ratpack.handling.Handler async(Function<Context, Observable<Result>> handler) {
        return ctx -> RxRatpack
            .promiseSingle(handler.apply(ctx))
            .onError(error -> {
                error.printStackTrace();
                ctx.getResponse()
                    .contentType("text/plain")
                    .status(500)
                    .send(Arrays.asList(error.getStackTrace()).stream().map(StackTraceElement::toString).collect(Collectors.joining("\n")));
            }).then(result -> {
                Response response = ctx.getResponse()
                    .contentType(result.contentType)
                    .status(result.status);
                result.cookies.forEach((k, v) -> response.getCookies().add(v));
                result.headers.forEach((k, v) -> response.getHeaders().add(k, v));
                if (result.direct == null) {
                    response.send(result.body);
                } else {
                    response.sendFile(result.direct.toPath());
                }
            });
    }
}
