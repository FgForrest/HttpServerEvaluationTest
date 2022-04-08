package one.edee.oss.http_server_evaulation_test.server.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpObject;

public interface PathHandler {

    void channelRead(ChannelHandlerContext ctx, HttpObject msg);
}
