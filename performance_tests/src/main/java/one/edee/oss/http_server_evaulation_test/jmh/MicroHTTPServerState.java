package one.edee.oss.http_server_evaulation_test.jmh;

public class MicroHTTPServerState extends ServerState {

    @Override
    protected String getServerPort() {
        return "8081";
    }
}
