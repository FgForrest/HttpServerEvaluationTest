package one.edee.oss.http_server_evaulation_test.server.undertow;

import io.undertow.Undertow;
import io.undertow.server.handlers.BlockingHandler;

import static io.undertow.Handlers.path;
import static java.util.Optional.ofNullable;

public class UndertowServerRunner {

    public static final int PORT = 8088;

    private Undertow server;

    public void run() {
        final int port = ofNullable(System.getProperty("port")).map(Integer::parseInt).orElse(PORT);;
        server = Undertow.builder()
                .addHttpListener(port, "localhost")
                .setHandler(path()
                        .addExactPath("/", new HelloWorldHandler())
                        .addExactPath("/graphql", new BlockingHandler(new GraphQLHandler())))
                .build();
        server.start();
        System.out.println("Undertow server started " + port + ".");
    }

    public void stop() {
        server.stop();
        System.out.println("Undertow server stopped.");
    }

    public static void main(String[] args) {
        new UndertowServerRunner().run();
    }
}
