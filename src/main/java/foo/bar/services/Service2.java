package foo.bar.services;

import foo.bar.lib.Result;
import foo.bar.lib.Service;
import foo.bar.lib.WS;
import okhttp3.Request;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import rx.Observable;

import static foo.bar.lib.Async.async;

public class Service2 extends Service {

    public Service2() {
        super(8889);
    }

    @Override
    public Chain routes(Chain chain) {
        return chain
            .get("bike-shelters", async(this::service));  // exposé sur l'url http://0.0.0.0:8889/bike-shelters
    }

    // Le service appele juste un service hebergé en tant que source de données
    public Observable<Result> service(Context ctx) {
        return WS.call(new Request.Builder()
            .url("http://open-data-poitiers.herokuapp.com/api/v2/bike-shelters/all")
            .build())
            .map(response -> Result.ok(response, "application/json"));
    }
}
