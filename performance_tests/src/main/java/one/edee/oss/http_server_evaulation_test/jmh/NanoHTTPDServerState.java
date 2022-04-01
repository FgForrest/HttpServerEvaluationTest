package one.edee.oss.http_server_evaulation_test.jmh;

public class NanoHTTPDServerState extends ServerState {

    @Override
    protected String getServerPort() {
        return "8083";
    }
}
