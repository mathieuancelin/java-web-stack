package foo.bar.services;

import com.google.common.base.Throwables;
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
            return chain
                .files(f -> f.dir("public").indexFiles("index.html"))
                .get("proxy", async(this::proxy));
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
