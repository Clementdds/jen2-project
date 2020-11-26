package fr.epita.broker;

import fr.epita.broker.database.Database;
import fr.epita.broker.server.BrokeService;
import fr.epita.broker.server.Endpoints;
import io.vertx.core.Vertx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class httpTest {

    @Test
    public void TestRoutes() throws IOException, InterruptedException {
        Database database = new Database();
        final var vertx = Vertx.vertx();
        vertx.deployVerticle(new Endpoints(new BrokeService(database)));

        // create a client
        var client = HttpClient.newHttpClient();

        // TEST TOPIC
        var body = HttpRequest.BodyPublishers.ofString(
                "{\n" +
                        "\t\"name\": \"topic_name1\",\n" +
                        "\t\"partitions\": 42\n" +
                        "}");

        var request = HttpRequest.newBuilder(
                URI.create("http://localhost:9999/topic"))
                                 .header("accept", "application/json")
                                 .method("POST", body)
                                 .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\"StatusCode\":200,\"Message\":\"ok\"}", response.body());

        body = HttpRequest.BodyPublishers.ofString(
                "{\n" +
                        "\t\"name\": \"topic_name2\"\n" +
                        "}");

        request = HttpRequest.newBuilder(
                URI.create("http://localhost:9999/topic"))
                                 .header("accept", "application/json")
                                 .method("POST", body)
                                 .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\"StatusCode\":200,\"Message\":\"ok\"}", response.body());

        //TEST ADD MESSAGES
        body = HttpRequest.BodyPublishers.ofString(
                "{\n" +
                        "\t\t\"messages\": [\n" +
                        "\t\t\"message1\",\n" +
                        "\t\t\"message2\",\n" +
                        "\t\t\"message3\",\n" +
                        "\t\t\"message4\",\n" +
                        "\t\t\"message5\",\n" +
                        "\t\t\"message6\",\n" +
                        "\t\t\"message7\",\n" +
                        "\t\t\"message8\",\n" +
                        "\t\t\"message9\",\n" +
                        "\t\t\"message10\",\n" +
                        "\t\t\"message11\",\n" +
                        "\t\t\"message12\"\n" +
                        "    ]\n" +
                        "}");

        request = HttpRequest.newBuilder(
                URI.create("http://localhost:9999/topic_name1/1"))
                             .header("accept", "application/json")
                             .method("POST", body)
                             .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\"items\":[0,1,2,3,4,5,6,7,8,9,10,11]}", response.body());

        request = HttpRequest.newBuilder(
                URI.create("http://localhost:9999/topic_name2/1"))
                             .header("accept", "application/json")
                             .method("POST", body)
                             .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\"items\":[0,1,2,3,4,5,6,7,8,9,10,11]}", response.body());

        //TEST SUBSCRIBE
        body = HttpRequest.BodyPublishers.ofString("");

        request = HttpRequest.newBuilder(
                URI.create("http://localhost:9999/topic/topic_name1/1/subscribe"))
                             .header("accept", "application/json")
                             .method("POST", body)
                             .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\"StatusCode\":200,\"groupId\":\"1\",\"subscriptionId\":0,\"errorMessage\":null}", response.body());

        request = HttpRequest.newBuilder(
                URI.create("http://localhost:9999/topic/topic_name1/1/subscribe"))
                             .header("accept", "application/json")
                             .method("POST", body)
                             .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\"StatusCode\":200,\"groupId\":\"1\",\"subscriptionId\":1,\"errorMessage\":null}", response.body());

        request = HttpRequest.newBuilder(
                URI.create("http://localhost:9999/topic/topic_name1/2/subscribe"))
                             .header("accept", "application/json")
                             .method("POST", body)
                             .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{\"StatusCode\":200,\"groupId\":\"2\",\"subscriptionId\":2,\"errorMessage\":null}", response.body());

        //TEST DELETE SUBSCRIBE
        request = HttpRequest.newBuilder(
                URI.create("http://localhost:9999/2"))
                             .header("accept", "application/json")
                             .method("DELETE", body)
                             .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("\"ok\"", response.body());

        //TEST FETCH MESSAGES
        request = HttpRequest.newBuilder(
                URI.create("http://localhost:9999/1?upTo=5&wait=11"))
                             .header("accept", "application/json")
                             .method("GET", body)
                             .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("[\"message1\",\"message2\",\"message3\",\"message4\",\"message5\"]", response.body());

        request = HttpRequest.newBuilder(
                URI.create("http://localhost:9999/0?upTo=5&wait=12"))
                             .header("accept", "application/json")
                             .method("GET", body)
                             .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("[\"message6\",\"message7\",\"message8\",\"message9\",\"message10\"]", response.body());

        request = HttpRequest.newBuilder(
                URI.create("http://localhost:9999/1?upTo=5&wait=13"))
                             .header("accept", "application/json")
                             .method("GET", body)
                             .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("[\"message11\",\"message12\"]", response.body());
    }
}
