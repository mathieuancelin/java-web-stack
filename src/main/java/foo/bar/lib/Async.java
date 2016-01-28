package foo.bar.lib;

import com.google.common.base.Joiner;
import ratpack.handling.Context;
import ratpack.http.Response;
import rx.Observable;

import java.util.Arrays;
import java.util.function.Function;

public class Async {
    public static ratpack.handling.Handler async(Function<Context, Observable<Result>> handler) {
        return ctx -> {
            try {
                Observable<Result> resultObservable = handler.apply(ctx);
                resultObservable.subscribe(
                        result -> {
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
                        },
                        error -> {
                            error.printStackTrace();
                            ctx.getResponse()
                                    .contentType("text/plain")
                                    .status(500)
                                    .send(Joiner.on("\n").join(Arrays.asList(error.getStackTrace())));
                        }
                );
            } catch (Exception e) {
                e.printStackTrace();
                ctx.getResponse()
                        .contentType("text/plain")
                        .status(500)
                        .send(Joiner.on("\n").join(Arrays.asList(e.getStackTrace())));
            }
        };
    }
}
