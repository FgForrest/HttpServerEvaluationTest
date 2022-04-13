package one.edee.oss.http_server_evaulation_test.jmh;

public class MicronautServerState extends ServerState {
    @Override
    protected int getServerPort() {
        return 8090;
    }
}
