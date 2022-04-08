package one.edee.oss.http_server_evaulation_test.jmh;

import one.edee.oss.http_server_evaulation_test.server.undertow.UndertowServerRunner;

public class UndertowServerState extends ServerState {

    @Override
    protected int getServerPort() {
        return UndertowServerRunner.PORT;
    }
}
