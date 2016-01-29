package foo.bar;

import foo.bar.services.*;

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

// TODO : json-lib
// TODO : sql-lib