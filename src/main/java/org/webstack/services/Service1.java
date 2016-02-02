package org.webstack.services;

import org.webstack.lib.Result;
import org.webstack.lib.Service;
import org.webstack.lib.WS;
import okhttp3.Request;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import rx.Observable;

import static org.webstack.lib.Async.async;

public class Service1 extends Service {

    public Service1() {
        super(8888);
    }

    @Override
    public Chain routes(Chain chain) {
        return chain
            .get("glass-containers", async(this::service)); // exposé sur l'url http://0.0.0.0:8888/glass-containers
    }

    // Le service appele juste un service hebergé en tant que source de données
    public Observable<Result> service(Context ctx) {
        return WS.call(new Request.Builder()
            .url("http://open-data-poitiers.herokuapp.com/api/v2/glass-containers/all")
            .build())
            .map(response -> Result.ok(response, "application/json"));
    }
}
