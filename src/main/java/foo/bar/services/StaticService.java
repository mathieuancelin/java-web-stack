package foo.bar.services;

import com.google.common.base.Throwables;
import foo.bar.lib.Service;
import ratpack.handling.Chain;

public class StaticService extends Service {

    @Override
    public String getHost() {
        return "0.0.0.0";
    }

    @Override
    public int getPort() {
        return 8886;
    }

    @Override
    public Chain routes(Chain chain) {
        try {
            return chain.files(f -> f.dir("public").indexFiles("index.html"));
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}