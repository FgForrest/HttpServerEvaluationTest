package one.edee.oss.http_server_evaulation_test.jmh;

import one.edee.oss.http_server_evaulation_test.server.vertx.VertXServerRunner;

public class VertXServerState extends ServerState {
    @Override
    protected int getServerPort() {
        return VertXServerRunner.PORT;
    }
}
