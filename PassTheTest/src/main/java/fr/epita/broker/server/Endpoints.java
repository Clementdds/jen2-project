package fr.epita.broker.server;

import fr.epita.utils.Json;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Data;

import java.util.List;

public class Endpoints extends AbstractVerticle {

    private final BrokeService brokeService;

    public Endpoints(final BrokeService brokeService) {
        this.brokeService = brokeService;
    }

    @Override
    public void start() {
        Router router = initEndpoints();
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router).listen(9999);
    }

    private Router initEndpoints() {
        final Router router = Router.router(vertx);

        /*
         * Create Topic
         */
        router.route(HttpMethod.POST, "/topic")
                .handler(BodyHandler.create())
                .handler(ctx -> {

                    final var json = ctx.getBodyAsString();
                    final var body = Json.decode(PostTopicRequest.class, json);
                    final var result = brokeService.postTopic(body.name, body.partitions);

                    ctx.response()
                            .setStatusCode(result.StatusCode)
                            .end(Json.encode(result));
                });

        /*
         * Fetch message
         */
        router.route(HttpMethod.GET, "/:subscriptionId?upTo=x&wait=y")
                .handler(BodyHandler.create())
                .handler(ctx -> {
                    final var subscriptionId = ctx.request().getParam("subscriptionId");
                    //final MultiMap params =  ctx.request().params();
                    final var upTo = ctx.request().getParam("upTo");
                    final var wait = ctx.request().getParam("wait");

                    final var result = brokeService.fetchMessages(subscriptionId, upTo, wait);

                    ctx.response()
                            .setStatusCode(result.StatusCode)
                            .end(Json.encode(result.Messages));
                });

        /*
         * Post message
         */
        router.route(HttpMethod.POST, "/:topicName/:groupId")
                .handler(BodyHandler.create())
                .handler(ctx -> {
                    final var json = ctx.getBodyAsString();
                    final var body = Json.decode(PostMessagesRequest.class, json);

                    final var topicName = ctx.request().getParam("topicName");
                    final var groupId = ctx.request().getParam("groupId");

                    final var result = brokeService.postMessages(body.messages, topicName, groupId);

                    final var response = new PostMessagesResponse();
                    response.setItems(result);

                    ctx.response()
                            .setStatusCode(HttpResponseStatus.OK.code())
                            .end(Json.encode(response));
                });

        /*
         * Create subscription
         */
        router.route(HttpMethod.POST, "/topic/:topicName/:groupId/subscribe")
                .handler(BodyHandler.create())
                .handler(ctx -> {
                    final var topicName = ctx.request().getParam("topicName");
                    final var groupId = ctx.request().getParam("groupId");

                    final var result = brokeService.postSubscription(topicName, groupId);

                    ctx.response()
                            .setStatusCode(result.StatusCode)
                            .end(Json.encode(result));
                });

        /*
         * Delete subscription
         */
        router.route(HttpMethod.DELETE, "/:subscriptionId")
                .handler(BodyHandler.create())
                .handler(ctx -> {
                    final var subscriptionId = ctx.request().getParam("subscriptionId");

                    final var result = brokeService.deleteSubscription(subscriptionId);

                    ctx.response()
                            .setStatusCode(result.StatusCode)
                            .end(Json.encode(result.Message));
                });
        return router;
    }

    @Data private static class PostTopicRequest {
        private String name;
        private int partitions;
    }

    @Data private static class PostMessagesRequest {
        private List<String> messages;
    }

    @Data private static class PostMessagesResponse {
        private List<Long> items;

        public void setItems(List<Long> items) {
            this.items = items;
        }
    }
}
