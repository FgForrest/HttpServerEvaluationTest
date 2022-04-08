package one.edee.oss.http_server_evaulation_test.server.microhttp;

import org.microhttp.Handler;
import org.microhttp.Header;
import org.microhttp.Request;
import org.microhttp.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DispatcherHandler implements Handler {

    private final ScheduledExecutorService executorService;
    private Map<String, PathHandler> handlers = new HashMap<>();

    public DispatcherHandler() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        handlers.put("/", new HelloWorldHandler());
        handlers.put("/graphql", new GraphQLHandler());
    }

    @Override
    public void handle(Request request, Consumer<Response> callback) {
        final PathHandler relevantHandler = handlers.get(request.uri());
        if (relevantHandler == null) {
            callback.accept(new Response(
                    404,
                    "NOT FOUND",
                    List.of(new Header("Content-Type", "text/plain")),
                    "".getBytes()
            ));
        }
        executorService.schedule(() -> callback.accept(relevantHandler.handle(request)), 0, TimeUnit.SECONDS);
    }
}
