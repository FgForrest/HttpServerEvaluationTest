package one.edee.oss.http_server_evaluation_test.server.armeria;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;

public class ArmeriaServerRunner {
    public static final int PORT = 8090;

    private Server server;

    public void run() {
        server = Server.builder()
                .http(PORT)
                .service("/", (ctx, req) -> {
                    ctx.addAdditionalResponseHeader("Content-Type", "text/plain");
                    return HttpResponse.of("Hello World");
                })
                .annotatedService(new GraphQLService())
                .build();
        server.start().join();
        System.out.println("Armeria server started " + PORT + ".");
    }

    public void stop() {
        server.stop().join();
        System.out.println("Armeria server stopped.");
    }

    public static void main(String[] args) {
        new ArmeriaServerRunner().run();
    }
}
