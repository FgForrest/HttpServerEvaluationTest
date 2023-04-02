package one.edee.oss.http_server_evaulation_test.jmh;

public class SpringBootMVCServerState extends ServerState {
    @Override
    protected int getServerPort() {
        return 8086;
    }
}
