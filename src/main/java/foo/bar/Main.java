package foo.bar;

import foo.bar.services.Service1;
import foo.bar.services.Service2;
import foo.bar.services.ServiceAggregator;
import foo.bar.services.StaticService;

public class Main {

    public static void main(String... args) {
        new Service1().run();
        new Service2().run();
        new ServiceAggregator().run();
        new StaticService().run();
    }
}
