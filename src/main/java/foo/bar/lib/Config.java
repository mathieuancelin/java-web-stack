package foo.bar.lib;

import com.typesafe.config.ConfigFactory;

public class Config {

    private static final com.typesafe.config.Config CONFIG =
            ConfigFactory.load().withFallback(ConfigFactory.empty());

    public static com.typesafe.config.Config config() {
        return CONFIG;
    }

    public static String mode() {
        String mode = CONFIG.getString("application.mode");
        if (mode == null) {
            return "dev";
        }
        return mode;
    }
}
