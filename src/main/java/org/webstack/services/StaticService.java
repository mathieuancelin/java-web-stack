package org.webstack.services;

import org.webstack.lib.Config;
import org.webstack.lib.Result;
import org.webstack.lib.Service;
import org.webstack.lib.WS;
import okhttp3.Request;
import org.webstack.lib.*;
import ratpack.handling.Chain;
import ratpack.handling.Context;
import rx.Observable;

import static org.webstack.lib.Async.async;

public class StaticService extends Service {

    public StaticService() {
        super(8886);
    }

    // service de proxy permettant d'appeler les autres service de la grappe tout en restant sur le meme port (JS)
    // pourrait etre remplacé par services cors
    public Observable<Result> proxy(Context ctx) {
        String url = ctx.getRequest().getQueryParams().get("url");
        return WS.call(new Request.Builder().url(url).build())
                .map(response -> Result.ok(response, "application/json"));
    }

    @Override
    public Chain routes(Chain chain) {
        chain.get("proxy", async(this::proxy));
        if (Config.config().mode().equalsIgnoreCase("dev")) { // si en mode dev, on expose une page utilisant le webpack-dev-server
            chain.get("dev", async(this::dev));
        }
        Utils.unsafe(() ->
            chain.files(f -> f.dir("public").indexFiles("index.html"))); // exposition du dossier resources/public comme dossier static web
        return chain;
    }

    // page de dev utilisant  webpack-dev-server comme source JS
    public Observable<Result> dev(Context ctx) {
        return Result.ok("<!DOCTYPE html><html><head><title>Webstack Test</title>" +
            "</head><body><div id=\"app\">Dev env ...</div>" +
            "<script src=\"http://localhost:8885/assets/app.js\"></script>" +
            "<script>App.init();</script></body></html>", "text/html").asObservable();
    }
}
