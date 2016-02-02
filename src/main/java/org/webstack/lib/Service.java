package org.webstack.lib;

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
    private final String host;
    private final int port;
    private final String configPath;

    public Service(String host, int port, String configPath) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.host = host;
        this.port = port;
        this.configPath = configPath;
    }

    public Service(String host, int port) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.host = host;
        this.port = port;
        this.configPath = "application";
    }

    public Service(int port) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.host = "0.0.0.0";
        this.port = port;
        this.configPath = "application";
    }

    public Service() {
        this.logger = LoggerFactory.getLogger(getClass());
        this.host = "0.0.0.0";
        this.port = 9000;
        this.configPath = "application";
    }

    public Service(String configPath) {
        this.logger = LoggerFactory.getLogger(getClass());
        this.host = "0.0.0.0";
        this.port = 9000;
        this.configPath = configPath;
    }

    public abstract Chain routes(Chain chain);

    public void registry(RegistrySpec registry) {}

    public RatpackServer run() {
        try {
            Path base = BaseDir.find("public");
            ServerConfig config = ServerConfig
                    .embedded()
                    .port(port)
                    .baseDir(base)
                    .development(Config.config().mode().equalsIgnoreCase("dev"))
                    .publicAddress(uri(host))
                    .threads(Runtime.getRuntime().availableProcessors() + 1)
                    .build();
            return RatpackServer.start(server -> server
                    .serverConfig(config)
                    .registryOf(r -> {
                        r.add(Config.class, new Config(configPath));
                        this.registry(r);
                    })
                    .handlers(this::routes)
            );
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

}
