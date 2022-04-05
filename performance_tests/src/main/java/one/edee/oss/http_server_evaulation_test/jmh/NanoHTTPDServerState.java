package one.edee.oss.http_server_evaulation_test.jmh;

import one.edee.oss.http_server_evaulation_test.server.nanohttpd.NanoHTTPDServerRunner;

public class NanoHTTPDServerState extends ServerState {

    @Override
    protected int getServerPort() {
        return NanoHTTPDServerRunner.PORT;
    }
}
