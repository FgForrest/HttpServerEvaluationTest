package one.edee.oss.http_server_evaulation_test.jmh;

public class NettyServerState extends ServerState {

    @Override
    protected String getServerPort() {
        return "8082";
    }
}
