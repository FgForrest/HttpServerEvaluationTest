package one.edee.oss.http_server_evaulation_test.jmh;

import one.edee.oss.http_server_evaulation_test.server.spring_boot_web_flux.SpringBootWebFluxServerRunner;

public class SpringBootWebFluxServerState extends ServerState{

    @Override
    protected int getServerPort() {
        return SpringBootWebFluxServerRunner.PORT;
    }
}
