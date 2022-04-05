package one.edee.oss.http_server_evaulation_test.jmh;

import one.edee.oss.http_server_evaulation_test.server.netty.NettyServerRunner;

public class NettyServerState extends ServerState {

    @Override
    protected int getServerPort() {
        return NettyServerRunner.PORT;
    }
}
