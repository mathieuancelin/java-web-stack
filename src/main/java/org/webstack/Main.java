package org.webstack;

import org.webstack.services.*;
import org.webstack.services.ServerAggregator;
import org.webstack.services.StaticServer;

public class Main {

    public static void main(String... args) {
        // ici on démarre tout nos services
        // chaque service est un serveur HTTP avec diverses routes
        new Server1().run(); // un service qui récupère ses données via un webservice hébergé
        new Server2().run(); // un service qui récupère ses données via un webservice hébergé
        new ServerAggregator().run(); // un service qui va aggérer et traiter les deux services précédants
        new StaticServer().run(); // un service qui sert l'application JS
    }
}

// TODO : sql-lib