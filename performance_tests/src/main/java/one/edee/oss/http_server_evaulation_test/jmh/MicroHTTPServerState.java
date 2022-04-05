package one.edee.oss.http_server_evaulation_test.jmh;

import one.edee.oss.http_server_evaulation_test.server.microhttp.MicroHTTPServerRunner;

public class MicroHTTPServerState extends ServerState {

    @Override
    protected int getServerPort() {
        return MicroHTTPServerRunner.PORT;
    }
}
