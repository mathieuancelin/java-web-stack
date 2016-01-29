package foo.bar;

import foo.bar.services.Service1;
import foo.bar.services.Service2;
import foo.bar.services.ServiceAggregator;
import foo.bar.services.StaticService;

public class Main {

    public static void main(String... args) {
        // ici on démarre tout nos services
        // chaque service est un serveur HTTP avec diverses routes
        new Service1().run(); // un service qui récupère ses données via un webservice hébergé
        new Service2().run(); // un service qui récupère ses données via un webservice hébergé
        new ServiceAggregator().run(); // un service qui va aggérer et traiter les deux services précédants
        new StaticService().run(); // un service qui sert l'application JS
    }
}

// TODO : fix async adapter blocking http thread
// TODO : fix client unqueue double transmission
// TODO : json-lib
// TODO : sql-lib