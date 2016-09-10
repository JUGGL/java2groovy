package us.juggl.twentysixteen.september.groovy;

import io.vertx.core.AbstractVerticle as AV;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * A simple example of a Vert.x based REST service
 */
public class SimpleREST extends AV {

    // A Multi-line string in Groovy
    private static final String SOME_HTML =
'''<html>
  <head>
    <title>My Title</title>
  </head>
  <body>
    Hello Groovy!
  </body>
</html>'''

    @Override
    void start() throws Exception {
        Router router = Router.router(vertx);

        router.get("/")                     .handler({ ctx ->
            ok(ctx, SOME_HTML, "text/html");
        });
        router.get("/rest/v1/customer")     .handler({ ctx ->
            ok(ctx, new JsonArray(customerList().collect { c -> new JsonObject(c) }).encodePrettily());
        });
        router.get("/rest/v1/customer/:id") .handler(this.&customerDetail);
        router.get("/rest/v1/product")      .handler(this.&productList);
        router.get("/rest/v1/product/:id")  .handler(this.&productDetail);
        router.get("/rest/v1/ping")         .handler(this.&sendPing);
        router.get("/rest/v1/ping/:content").handler(this.&sendPing);

        vertx.createHttpServer().requestHandler(router.&accept).listen(8080);
        vertx.deployVerticle("us.juggl.twentysixteen.september.groovy.AsyncVerticle");
    }

    private void sendPing(RoutingContext ctx) {
        vertx.eventBus().send("us.juggl.endpoint1", ctx.pathParam("content"), { reply ->
            ok(ctx, (String)reply.result().body(), "text/plain");
        });
    }

    private void productDetail(RoutingContext ctx) {
        // MAP LITERALS!!! Define a map and never have to use `.put()` again!!!
        def data = [
            "id": "1",
            "name": "Widget",
            "short_description": "A Simple widget",
            "long_description": "This is our standard everyday use sort of widget"
        ];
        ok(ctx, new JsonObject(data).encodePrettily());
    }

    private void productList(RoutingContext ctx) {
        def data = [
            [
                "id": "1",
                "name": "Widget",
                "short_description": "A Simple widget",
                "long_description": "This is our standard everyday use sort of widget"
            ],
            [
                "id": "2",
                "name": "Premium Widget",
                "short_description": "A Premium widget",
                "long_description": "This is our PREMIUM widget"
            ],
            [
                "id": "3",
                "name": "Budget Widget",
                "short_description": "A Budget widget",
                "long_description": "This is our lower quality cheap widget"
            ]
        ]
        ok(ctx, new JsonArray(data.collect { p -> new JsonObject(p) }).encodePrettily());
    }

    private void customerDetail(RoutingContext ctx) {
        def data = [
            "id": "1",
            "name": "John Smith",
            "given_name": "John",
            "family_name": "Smith"
        ]
        ok(ctx, new JsonObject(data).encodePrettily());
    }

    private Map[] customerList() {
        def data = [
            customerRecord(),
            customerRecord("Jane", "Doe", "2"),
            customerRecord("Billy", "Connolly", "3")
        ];

        return data;
    }

    // Groovy has default parameter values, so we no longer need the overloaded methods from Java
    private Map customerRecord(givenName = "John", familyName = "Smith", id = "1") {
        def customer = [
            "id": id,
            "name": "${givenName} ${familyName}".toString(),
            "given_name": givenName,
            "family_name": familyName
        ];
        return customer;
    }

    // Same here, no overloaded method needed anymore because we use the default parameter value
    private void ok(RoutingContext ctx, String body, contentType = "application/json") {
        ctx.response()
                .putHeader("Content-Type", contentType)
                .setStatusMessage(OK.reasonPhrase)
                .setStatusCode(OK.code)
                .end(body);
    }
}
