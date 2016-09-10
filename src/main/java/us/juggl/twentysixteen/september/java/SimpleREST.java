package us.juggl.twentysixteen.september.java;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * A simple example of a Vert.x based REST service
 */
public class SimpleREST extends AbstractVerticle {

    // A Multi-line string in Java
    private static final String SOME_HTML = "<html>\n" +
            "  <head>\n" +
            "    <title>My Title</title>\n" +
            "  </head>\n" +
            "  <body>\n" +
            "    Hello Java!\n" +
            "  </body>\n" +
            "</html>\n";

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.get("/")                     .handler(ctx -> {
            ctx.response().setStatusCode(OK.code()).setStatusMessage(OK.reasonPhrase()).end(SOME_HTML);
        });
        router.get("/rest/v1/customer")     .handler(ctx -> {
            ok(ctx, new JsonArray(Arrays.asList(customerList())).encodePrettily());
        });
        router.get("/rest/v1/customer/:id") .handler(this::customerDetail);
        router.get("/rest/v1/product")      .handler(this::productList);
        router.get("/rest/v1/product/:id")  .handler(this::productDetail);
        router.get("/rest/v1/ping")         .handler(this::sendPing);
        router.get("/rest/v1/ping/:content").handler(this::sendPing);

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        vertx.deployVerticle("us.juggl.twentysixteen.september.groovy.AsyncVerticle");
    }

    private void sendPing(RoutingContext ctx) {
        vertx.eventBus().send("us.juggl.endpoint1", ctx.pathParam("content"), reply -> {
            ok(ctx, (String)reply.result().body(), "text/plain");
        });
    }

    private void productDetail(RoutingContext ctx) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "1");
        data.put("name", "Widget");
        data.put("short_description", "A Simple widget");
        data.put("long_description", "This is our standard everyday use sort of widget");
        ok(ctx, new JsonObject(data).encodePrettily());
    }

    private void productList(RoutingContext ctx) {
        Map<String, Object>[] data = new HashMap[3];
        data[0] = new HashMap<>();
        data[0].put("id", "1");
        data[0].put("name", "Premium Widget");
        data[0].put("short_description", "A Premium widget");
        data[0].put("long_description", "This is our PREMIUM widget");

        data[1] = new HashMap<>();
        data[1].put("id", "2");
        data[1].put("name", "Budget Widget");
        data[1].put("short_description", "A Budget widget");
        data[1].put("long_description", "This is our lower quality cheap widget");

        data[2] = new HashMap<>();
        data[2].put("id", "3");
        data[2].put("name", "Widget");
        data[2].put("short_description", "A Simple widget");
        data[2].put("long_description", "This is our standard everyday use sort of widget");

        ok(ctx, new JsonArray(Arrays.asList(data)).encodePrettily());
    }

    private void customerDetail(RoutingContext ctx) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "1");
        data.put("name", "John Smith");
        data.put("given_name", "John");
        data.put("family_name", "Smith");
        ok(ctx, new JsonObject(data).encodePrettily());
    }

    private Map<String, String>[] customerList() {
        Map<String, String>[] data = new HashMap[3];
        data[0] = customerRecord();
        data[1] = customerRecord("Jane", "Doe", "2");
        data[2] = customerRecord("Billy", "Connolly", "3");

        return data;
    }

    private Map<String, String> customerRecord(String givenName, String familyName, String id) {
        Map<String, String> customer = new HashMap<>();
        customer.put("id", id);
        customer.put("name", givenName+" "+familyName);
        customer.put("given_name", givenName);
        customer.put("family_name", familyName);
        return customer;
    }

    private Map<String, String> customerRecord() {
        return customerRecord("John", "Smith", "1");
    }

    private void ok(RoutingContext ctx, String body) {
        ok(ctx, body, "application/json");
    }

    private void ok(RoutingContext ctx, String body, String contentType) {
        ctx.response()
                .putHeader("Content-Type", contentType)
                .setStatusMessage(OK.reasonPhrase())
                .setStatusCode(OK.code())
                .end(body);
    }
}
