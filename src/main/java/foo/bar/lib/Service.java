package foo.bar.lib;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ratpack.handling.Chain;
import ratpack.registry.RegistrySpec;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;
import ratpack.server.ServerConfig;

import java.net.URI;
import java.nio.file.Path;

public abstract class Service {

    private static URI uri(String uri) {
        try {
            return new URI(uri);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    public final Logger logger;

    public Service() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public String getHost() {
        return "0.0.0.0";
    }

    public int getPort() {
        return 9000;
    }

    public abstract Chain routes(Chain chain);

    public void registry(RegistrySpec registry) {}

    public RatpackServer run() {
        try {
            Path base = BaseDir.find("public");
            ServerConfig config = ServerConfig
                    .embedded()
                    .port(getPort())
                    .baseDir(base)
                    .publicAddress(uri(getHost()))
                    .threads(Runtime.getRuntime().availableProcessors() + 1)
                    .build();
            return RatpackServer.start(server -> server
                    .serverConfig(config)
                    .registryOf(this::registry)
                    .handlers(this::routes)
            );
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

}
