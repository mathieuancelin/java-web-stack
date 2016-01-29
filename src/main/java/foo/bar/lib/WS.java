package foo.bar.lib;

import okhttp3.*;
import rx.Observable;

import java.io.IOException;

public class WS {
    private static OkHttpClient client = new OkHttpClient();
    public static Observable<Response> call(Request request) {
        return Observable.create(subscriber -> {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    subscriber.onNext(response);
                    subscriber.onCompleted();
                }
            });
        });
    }
}
