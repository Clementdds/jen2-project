package fr.epita.broker;

import fr.epita.broker.server.BrokeService;
import fr.epita.broker.server.Endopints;
import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) {
        final var vertx = Vertx.vertx();
        vertx.deployVerticle(new Endopints(new BrokeService()));
    }
}
