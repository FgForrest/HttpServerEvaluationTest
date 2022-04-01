package one.edee.oss.http_server_evaulation_test.server.microhttp;

import org.microhttp.Handler;
import org.microhttp.Request;
import org.microhttp.Response;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public abstract class AsyncHandler implements Handler {

    private final ScheduledExecutorService executorService;

    public AsyncHandler() {
        executorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void handle(Request request, Consumer<Response> callback) {
        // todo lho: docs states there should be 1 second delay, dont understand why yet
        executorService.schedule(() -> callback.accept(doHandle(request)), 0, TimeUnit.SECONDS);
    }

    protected abstract Response doHandle(Request request);
}
