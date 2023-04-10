package one.edee.oss.http_server_evaulation_test.server.javalin;

import io.javalin.Javalin;

import static java.util.Optional.ofNullable;

public class JavalinServerRunner {

    public static final int PORT = 8084;

    private Javalin app;

    public void run() {
        final Integer port = ofNullable(System.getProperty("port")).map(Integer::parseInt).orElse(PORT);
        app = Javalin.create()
                .get("/", new HelloWorldHandler())
                .post("/graphql", new GraphQLHandler())
                .start(port);
        System.out.println("Starting Javalin server on port " + port + ".");
    }

    public void stop() {
        app.stop();
        System.out.println("Javalin server stopped.");
    }

    public static void main(String[] args) {
        new JavalinServerRunner().run();
    }
}
