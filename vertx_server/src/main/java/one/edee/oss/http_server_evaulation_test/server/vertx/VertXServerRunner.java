package one.edee.oss.http_server_evaulation_test.server.vertx;

import graphql.GraphQL;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.graphql.GraphQLHandler;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLProvider;

import static java.util.Optional.ofNullable;

public class VertXServerRunner {

    public static final int PORT = 8085;

    private final Vertx vertx = Vertx.vertx();
    private final GraphQL graphQL;

    public VertXServerRunner() {
        this.graphQL = new GraphQLProvider().getGraphQL();
    }

    public void run() {
        vertx.deployVerticle(new AbstractVerticle() {
            @Override
            public void start() {
                // Create a Router
                Router router = Router.router(vertx);

                // Mount the handler for all incoming requests at every path and HTTP method
                router.get("/")
                        .handler(new HelloWorldHandler());

                router.post("/graphql")
                        // reads entire request body for later processing
                        .handler(BodyHandler.create())
                        // serialization and executing requests against graphql is handled by built-in handler
                        .blockingHandler(GraphQLHandler.create(graphQL));

                // Create the HTTP server
                final int port = ofNullable(System.getProperty("port")).map(Integer::parseInt).orElse(PORT);;
                vertx.createHttpServer()
                        // Handle every request using the router
                        .requestHandler(router)
                        // Start listening
                        .listen(port)
                        // Print the port
                        .onSuccess(server -> System.out.println("VertX server started on port " + port + "."));
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
