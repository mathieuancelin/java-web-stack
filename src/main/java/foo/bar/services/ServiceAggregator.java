package foo.bar.services;

import foo.bar.lib.*;
import okhttp3.Request;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import rx.Observable;

import static foo.bar.lib.Async.async;

public class ServiceAggregator extends Service {

    public ServiceAggregator() {
        super(8887);
    }

    @Override
    public Chain routes(Chain chain) {
        return chain
            .get("service", async(this::service))
            .get("mode", ctx -> {
                ctx.render(Config.config().mode());
            });
    }

    public Observable<Result> service(Context ctx) {

        Observable<Result> call1 = WS.call(new Request.Builder()
            .url("http://localhost:8888/glass-containers")
            .build())
            .map(response -> Result.ok(response, "application/json"));

        Observable<Result> call2 = WS.call(new Request.Builder()
            .url("http://localhost:8889/bike-shelters")
            .build())
            .map(response -> Result.ok(response,  "application/json"));

        return Observable.combineLatest(call1, call2, (res1, res2) -> Result.ok(
            Json.obj(
                Json.pair("glassContainers", Json.parse(res1.body)),
                Json.pair("bikerShelters", Json.parse(res2.body))
            )
        ));
    }
}
