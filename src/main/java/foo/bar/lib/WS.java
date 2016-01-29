package foo.bar.lib;

import okhttp3.*;
import rx.Observable;

import java.io.IOException;

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
    public static Observable<Response> asyncCall(Request request) {
        return Observable.create(subscriber -> {
            try {
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("onFailure");
                        e.printStackTrace();
                        subscriber.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.out.println("onResponse");
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
