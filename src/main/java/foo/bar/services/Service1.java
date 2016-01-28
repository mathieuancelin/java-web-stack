package foo.bar.services;

import foo.bar.lib.Result;
import foo.bar.lib.Service;
import foo.bar.lib.WS;
import okhttp3.Request;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import rx.Observable;

import static foo.bar.lib.Async.async;

public class Service1 extends Service {

    @Override
    public String getHost() {
        return "0.0.0.0";
    }

    @Override
    public int getPort() {
        return 8888;
    }

    @Override
    public Chain routes(Chain chain) {
        return chain.get("glass-containers", async(this::service));
    }

    public Observable<Result> service(Context ctx) {
        return WS.call(new Request.Builder()
            .url("http://open-data-poitiers.herokuapp.com/api/v2/glass-containers/all")
            .build())
            .map(r -> Result.ok(r, "application/json"));
    }
}