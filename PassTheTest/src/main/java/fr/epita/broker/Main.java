package fr.epita.broker;

import fr.epita.broker.database.Database;
import fr.epita.broker.server.BrokeService;
import fr.epita.broker.server.Endpoints;
import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) {
        Database database = new Database();
        final var vertx = Vertx.vertx();
        vertx.deployVerticle(new Endpoints(new BrokeService(database)));
    }
}
