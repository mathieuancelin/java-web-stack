package foo.bar.lib;

import okhttp3.*;
import rx.Observable;

import java.io.IOException;

public class WS {
    private static OkHttpClient client = new OkHttpClient();
    public static Observable<Response> call(Request request) {
        return Observable.create(subscriber -> {
            try {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        subscriber.onNext(response);
                        subscriber.onCompleted();
                    }
                });
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
