package foo.bar.services;

import foo.bar.lib.Result;
import foo.bar.lib.Service;
import foo.bar.lib.WS;
import okhttp3.Request;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import rx.Observable;

import static foo.bar.lib.Async.async;

// TODO : fix async adapter blocking http thread
// TODO : fix client unqueue double transmission
public class Tests extends Service {
    
    public Tests() {
        super(9999);
    }

    @Override
    public Chain routes(Chain chain) {
        return chain
            .get("non-blocking", ctx -> ctx.render("time: " + System.currentTimeMillis()))
            .get("async-ws", async(this::asyncWs))
            .get("rx-blocking", async(ctx -> {
                logger.info("creating");
                Observable<Result> obs = Observable.create(sub -> {
                    logger.info("Will wait 5 with obs");
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        sub.onError(e);
                    }
                    logger.info("Wait done with obs");
                    sub.onNext(Result.ok("done"));
                    sub.onCompleted();
                });
                logger.info("creating done");
                return obs;
            }))
            .get("blocking", ctx -> {
                logger.info("Will wait 5");
                Thread.sleep(5000);
                logger.info("Wait done");
                ctx.render("done");
            });
    }

    public Observable<Result> asyncWs(Context ctx) {
        return WS.asyncCall(new Request.Builder()
                .url("http://localhost:9999/non-blocking")
                .build())
                .map(response -> Result.ok(response, "text/plain"));
    }
}
