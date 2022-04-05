package one.edee.oss.http_server_evaulation_test.jmh;

import one.edee.oss.http_server_evaulation_test.server.javalin.JavalinServerRunner;

public class JavalinServerState extends ServerState {
    @Override
    protected int getServerPort() {
        return JavalinServerRunner.PORT;
    }
}
