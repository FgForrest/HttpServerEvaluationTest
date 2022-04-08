package one.edee.oss.http_server_evaulation_test.server.javalin;

import io.javalin.Javalin;

public class JavalinServerRunner {

    public static final int PORT = 8084;

    private Javalin app;

    public void run() {
        app = Javalin.create()
                .get("/", new HelloWorldHandler())
                .post("/graphql", new GraphQLHandler())
                .start(PORT);
    }

    public void stop() {
        app.stop();
        System.out.println("Javalin server stopped.");
    }

    public static void main(String[] args) {
        new JavalinServerRunner().run();
    }
}
