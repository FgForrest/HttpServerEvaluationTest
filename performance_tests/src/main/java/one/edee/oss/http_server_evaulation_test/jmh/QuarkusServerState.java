package one.edee.oss.http_server_evaulation_test.jmh;

import one.edee.oss.http_server_evaulation_test.server.quarkus.QuarkusServerRunner;

public class QuarkusServerState extends ServerState {
    @Override
    protected int getServerPort() {
        return QuarkusServerRunner.PORT;
    }
}
