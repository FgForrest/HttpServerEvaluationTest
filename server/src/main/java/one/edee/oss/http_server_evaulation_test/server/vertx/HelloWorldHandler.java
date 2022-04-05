package one.edee.oss.http_server_evaulation_test.server.vertx;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class HelloWorldHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext ctx) {
        final HttpServerResponse response = ctx.response();
        response.setStatusCode(200);
        response.end("Hello world");
    }
}
