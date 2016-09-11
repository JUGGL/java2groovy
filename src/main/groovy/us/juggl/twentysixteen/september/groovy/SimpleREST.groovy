package us.juggl.twentysixteen.september.groovy

import io.vertx.core.AbstractVerticle as AV
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import us.juggl.twentysixteen.september.groovy.beans.Address
import us.juggl.twentysixteen.september.groovy.beans.Customer

import static groovy.json.JsonOutput.prettyPrint
import static groovy.json.JsonOutput.toJson
import static io.netty.handler.codec.http.HttpResponseStatus.OK
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
            // Super simple JSON serialization! Have Bean/Map/List/Primitive, will serialize!
            ok(ctx, prettyPrint(toJson(customerList())));
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
        // Converters! Instead of returning a Map, a map literal can be "converted" into a specific type!
        def data = [
            id: 1,
            name: 'Widget',
            short_description: 'A Simple widget',
            long_description: 'This is our standard everyday use sort of widget'
        ] as JsonObject
        ok(ctx, data.encodePrettily());
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
        ok(ctx, prettyPrint(toJson(data)));
    }

    private void customerDetail(RoutingContext ctx) {
        // Another "converter" example, this time using a Bean. Take note of the nested Beans...
        def data = [
            id: 1,
            givenName: "John",
            familyName: "Smith",
            address: [
                street1: '123 East Main St.',
                city: 'Anytown',
                postCode: '12345',
                country: 'US'
            ] as Address
        ] as Customer
        ok(ctx, prettyPrint(toJson(data)));
    }

    private Customer[] customerList() {
        def data = [
            customerRecord(),
            customerRecord("Jane", "Doe", 2),
            customerRecord("Billy", "Connolly", 3)
        ];

        return data;
    }

    // Groovy has default parameter values, so we no longer need the overloaded methods from Java
    private Customer customerRecord(givenName = "John", familyName = "Smith", id = 1) {
        def customer = new Customer(givenName: givenName, familyName: familyName, id: id)
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
