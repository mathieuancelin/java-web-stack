package org.webstack.services;

import org.webstack.lib.Result;
import org.webstack.lib.Server;
import org.webstack.lib.WS;
import okhttp3.Request;
import org.webstack.lib.Async;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import rx.Observable;

public class Server2 extends Server {

    public Server2() {
        super(8889);
    }

    @Override
    public Chain routes(Chain chain) {
        return chain
            .get("bike-shelters", Async.async(this::service));  // exposé sur l'url http://0.0.0.0:8889/bike-shelters
    }

    // Le service appele juste un service hebergé en tant que source de données
    public Observable<Result> service(Context ctx) {
        return WS.call(new Request.Builder()
            .url("http://open-data-poitiers.herokuapp.com/api/v2/bike-shelters/all")
            .build())
            .map(response -> Result.ok(response, "application/json"));
    }
}
