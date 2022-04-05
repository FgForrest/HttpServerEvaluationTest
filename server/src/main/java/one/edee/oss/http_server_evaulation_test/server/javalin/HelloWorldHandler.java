package one.edee.oss.http_server_evaulation_test.server.javalin;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class HelloWorldHandler implements Handler {

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        ctx.result("Hello world");
    }
}
