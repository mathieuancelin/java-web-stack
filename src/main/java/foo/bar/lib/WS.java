package foo.bar.lib;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

public class WS {
    private static OkHttpClient client = new OkHttpClient();
    public static Observable<Response> call(Request request) {
        return Observable.create(subscriber -> {
            try {
                Response response = client.newCall(request).execute();
                subscriber.onNext(response);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
