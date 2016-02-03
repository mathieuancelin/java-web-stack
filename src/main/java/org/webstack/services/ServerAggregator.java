package org.webstack.services;

import okhttp3.Request;
import org.reactivecouchbase.json.Json;
import org.reactivecouchbase.json.Syntax;
import org.webstack.lib.*;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import rx.Observable;

import static org.webstack.lib.Async.async;

public class ServerAggregator extends Server {

    public ServerAggregator() {
        super(8887);
    }

    @Override
    public Chain routes(Chain chain) {
        return chain
            .get("service", async(this::service)) // exposé sur l'url http://0.0.0.0:8887/service
            .get("mode", ctx -> { // expose le mode de l'application sur http://0.0.0.0:8887/mode
                ctx.render(Config.config().mode());
            });
    }

    // Le service appele deux webservices exposés et les aggrège dans un seul objet json
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
                Syntax.$("glassContainers", Json.parse(res1.body)),
                Syntax.$("bikerShelters", Json.parse(res2.body))
            )
        ));
    }
}
