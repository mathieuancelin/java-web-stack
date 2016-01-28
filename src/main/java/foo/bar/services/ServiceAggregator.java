package foo.bar.services;

import foo.bar.lib.*;
import okhttp3.Request;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import rx.Observable;

import static foo.bar.lib.Async.async;

public class ServiceAggregator extends Service {

    @Override
    public String getHost() {
        return "0.0.0.0";
    }

    @Override
    public int getPort() {
        return 8887;
    }

    @Override
    public Chain routes(Chain chain) {
        return chain
            .get("service", async(this::service))
            .get("about", ctx -> {
                ctx.render(Config.config().getString("application.name"));
            });
    }

    public Observable<Result> service(Context ctx) {

        Observable<Result> call1 = WS.call(new Request.Builder()
            .url("http://localhost:8888/glass-containers")
            .build())
            .map(r -> Result.ok(r, "application/json"));

        Observable<Result> call2 = WS.call(new Request.Builder()
            .url("http://localhost:8889/bike-shelters")
            .build())
            .map(r -> Result.ok(r,  "application/json"));

        return Observable.combineLatest(call1, call2, (res1, res2) -> Result.ok(
            Json.obj(
                Json.pair("glassContainers", Json.parse(res1.body)),
                Json.pair("bikerShelters", Json.parse(res2.body))
            )
        ));
    }
}
