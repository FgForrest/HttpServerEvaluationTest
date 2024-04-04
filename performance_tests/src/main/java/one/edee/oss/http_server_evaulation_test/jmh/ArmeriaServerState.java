package one.edee.oss.http_server_evaulation_test.jmh;

import one.edee.oss.http_server_evaluation_test.server.armeria.ArmeriaServerRunner;

public class ArmeriaServerState extends ServerState {

    @Override
    protected int getServerPort() {
        return ArmeriaServerRunner.PORT;
    }
}
