package foo.bar.services;

import com.google.common.base.Throwables;
import foo.bar.lib.Config;
import foo.bar.lib.Result;
import foo.bar.lib.Service;
import foo.bar.lib.WS;
import okhttp3.Request;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import rx.Observable;

import static foo.bar.lib.Async.async;

public class StaticService extends Service {

    public StaticService() {
        super(8886);
    }

    public Observable<Result> proxy(Context ctx) {
        String url = ctx.getRequest().getQueryParams().get("url");
        return WS.call(new Request.Builder().url(url).build())
                .map(response -> Result.ok(response, "application/json"));
    }

    @Override
    public Chain routes(Chain chain) {
        try {
            chain
                .files(f -> f.dir("public").indexFiles("index.html"))
                .get("proxy", async(this::proxy));
            if (Config.config().mode().equalsIgnoreCase("dev")) {
                chain.get("dev", async(this::dev));
            }
            return chain;
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    public Observable<Result> dev(Context ctx) {
        return Result.ok("<!DOCTYPE html><html><head><title>Webstack Test</title>" +
            "</head><body><div id=\"app\">Dev env ...</div>" +
            "<script src=\"http://localhost:8885/assets/app.js\"></script>" +
            "<script>App.init();</script></body></html>", "text/html").asObservable();
    }
}
