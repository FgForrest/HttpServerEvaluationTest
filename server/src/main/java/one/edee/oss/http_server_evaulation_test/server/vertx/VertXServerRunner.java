package one.edee.oss.http_server_evaulation_test.server.vertx;

import graphql.GraphQL;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.graphql.GraphQLHandler;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLProvider;

public class VertXServerRunner {

    public static final int PORT = 8085;

    private Vertx vertx;
    private GraphQL graphQL;

    public VertXServerRunner() {
        this.graphQL = new GraphQLProvider().getGraphQL();
    }

    public void run() {
        vertx = Vertx.vertx();
        vertx.deployVerticle(new AbstractVerticle() {
            @Override
            public void start() throws Exception {
                // Create a Router
                Router router = Router.router(vertx);

                // Mount the handler for all incoming requests at every path and HTTP method
                router.get("/")
                        .handler(new HelloWorldHandler());

                router.post("/graphql")
                        // reads entire request body for later processing
                        .handler(BodyHandler.create())
                        // serialization and executing requests against graphql is handled by built-in handler
                        .handler(GraphQLHandler.create(graphQL));

                // Create the HTTP server
                vertx.createHttpServer()
                        // Handle every request using the router
                        .requestHandler(router)
                        // Start listening
                        .listen(PORT)
                        // Print the port
                        .onSuccess(server -> System.out.println("VertX server started."));
            }
        });
    }

    public void stop() {
        vertx.close(event -> System.out.println("VertX server stopped."));
    }

    public static void main(String[] args) {
        new VertXServerRunner().run();
    }
}
