package org.webstack.lib;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.io.IOException;

public class WS {
    private static final Logger LOGGER = LoggerFactory.getLogger(WS.class);
    private static OkHttpClient client = new OkHttpClient();
    public static Observable<Response> call(Request request) {
        return Observable.create(subscriber -> client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LOGGER.error("Error while calling '" + call.request().url() + "' : ", e);
                subscriber.onError(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        }));
    }
}
