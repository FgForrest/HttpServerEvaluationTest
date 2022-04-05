package one.edee.oss.http_server_evaulation_test.jmh;

import one.edee.oss.http_server_evaulation_test.server.spring_boot_mvc.SpringBootMVCServerRunner;

public class SpringBootMVCServerState extends ServerState {
    @Override
    protected int getServerPort() {
        return SpringBootMVCServerRunner.PORT;
    }
}
