package us.juggl.twentysixteen.september.groovy;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

/**
 * A simple example of a Vert.x based REST service
 */
public class SimpleREST extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.get("/rest/v1/customer")     .handler(this::customerList);
        router.get("/rest/v1/customer/:id") .handler(this::customerDetail);
        router.get("/rest/v1/product")      .handler(this::productList);
        router.get("/rest/v1/product/:id")  .handler(this::productDetail);

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }

    private void productDetail(RoutingContext ctx) {
        ok(ctx, new JsonObject().encodePrettily());
    }

    private void productList(RoutingContext ctx) {
        ok(ctx, new JsonArray().encodePrettily());
    }

    private void customerDetail(RoutingContext ctx) {
        ok(ctx, new JsonObject().encodePrettily());
    }

    private void customerList(RoutingContext ctx) {
        ok(ctx, new JsonArray().encodePrettily());
    }

    private void ok(RoutingContext ctx, String body) {
        ctx.response()
                .putHeader("Content-Type", "application/json")
                .setStatusMessage(OK.reasonPhrase())
                .setStatusCode(OK.code())
                .end(body);
    }
}
