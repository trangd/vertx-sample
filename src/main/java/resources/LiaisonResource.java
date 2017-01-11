package resources;

import Services.LiaisonService;
import beans.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.io.File;
import java.util.*;


public class LiaisonResource extends AbstractVerticle {

    LiaisonService liaisonService;

    @Override
    public void start(Future fut) {
        Router router = Router.router(vertx);

        router.route("/assets/*").handler(StaticHandler.create("assets"));

        router.get("/rest/parent").handler(this::getAllParent);
        router.route("/rest/parent*").handler(BodyHandler.create());
        router.get("/rest/parent/:name").handler(this::getParentMessage);
        router.put("/rest/parent").handler(this::confirmMessage);

        router.get("/rest/professor").handler(this::getAllMessage);
        router.route("/rest/professor*").handler(BodyHandler.create());
        router.post("/rest/professor").handler(this::addMessage);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", 8090),
                        result -> {
                            if (result.succeeded()) {
                                fut.complete();
                            } else {
                                fut.fail(result.cause());
                            }
                });

    }

    private void confirmMessage(RoutingContext routingContext){
        List<Message> messagesOfParent = liaisonService.getInstance().jsonToListMessage(routingContext.getBodyAsString());
        List<Message> messages = liaisonService.getInstance().updateMessages(messagesOfParent);
        liaisonService.getInstance().saveToJson(messages);
    }

    private void getAllParent(RoutingContext routingContext){
        String[] allParents = liaisonService.getInstance().getAllParents();
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(allParents));
    }

    private void getParentMessage(RoutingContext routingContext) {
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(
                        liaisonService.getInstance().retrieveParentMessages(routingContext.pathParam("name"))
                ));
    }

    private void getAllMessage(RoutingContext routingContext) {
        List<Message> messages = liaisonService.getInstance().getMessages();
        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(messages));
    }

    private void addMessage(RoutingContext routingContext) {
        final Message newMessage = Json.decodeValue(routingContext.getBodyAsString(),
                Message.class);
        List<Message> messages = liaisonService.getInstance().getMessages();
        messages.add(liaisonService.getInstance().updateNewMessage(newMessage, messages));
        liaisonService.getInstance().saveToJson(messages);
    }

}
