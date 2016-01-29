package foo.bar.lib;

import com.google.common.base.Joiner;
import ratpack.exec.Promise;
import ratpack.handling.Context;
import ratpack.http.Response;
import ratpack.rx.RxRatpack;
import rx.Observable;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class Async {

    public static final ExecutorService EXECUTOR_SERVICE =
        Executors.newFixedThreadPool(20);

    static {
        RxRatpack.initialize();
    }

    public static ratpack.handling.Handler async(Function<Context, Observable<Result>> handler) {
        return ctx -> {
            RxRatpack.promiseSingle(handler.apply(ctx))
                .onError(error -> {
                    error.printStackTrace();
                    ctx.getResponse()
                            .contentType("text/plain")
                            .status(500)
                            .send(Joiner.on("\n").join(Arrays.asList(error.getStackTrace())));
                })
                .then(result -> {
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
        };
    }
}
