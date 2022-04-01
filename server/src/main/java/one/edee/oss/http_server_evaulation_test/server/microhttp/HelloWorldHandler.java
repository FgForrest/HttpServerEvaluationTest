package one.edee.oss.http_server_evaulation_test.server.microhttp;

import org.microhttp.Header;
import org.microhttp.Request;
import org.microhttp.Response;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class HelloWorldHandler extends AsyncHandler {

    @Override
    protected Response doHandle(Request request) {
        String body = "Hello world:\n" +
                "method: " + request.method() + "\n" +
                "uri: " + request.uri() + "\n" +
                "body: " + (request.body() == null ? "" : new String(request.body(), StandardCharsets.UTF_8)) + "\n";
        return new Response(
                200,
                "OK",
                List.of(new Header("Content-Type", "text/plain")),
                body.getBytes()
        );
    }
}
