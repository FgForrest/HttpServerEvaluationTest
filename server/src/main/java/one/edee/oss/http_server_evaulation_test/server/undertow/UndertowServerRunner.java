package one.edee.oss.http_server_evaulation_test.server.undertow;

import io.undertow.Undertow;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.server.handlers.PathHandler;

import static io.undertow.Handlers.path;

public class UndertowServerRunner {

    public static final int PORT = 8088;

    private Undertow server;

    public void run() {
        server = Undertow.builder()
                .addHttpListener(PORT, "localhost")
                .setHandler(path()
                        .addExactPath("/", new HelloWorldHandler())
                        .addExactPath("/graphql", new BlockingHandler(new GraphQLHandler())))
                .build();
        server.start();
        System.out.println("Undertow server started.");
    }

    public void stop() {
        server.stop();
        System.out.println("Undertow server stopped.");
    }

    public static void main(String[] args) {
        new UndertowServerRunner().run();
    }
}
