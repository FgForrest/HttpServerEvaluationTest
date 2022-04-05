package one.edee.oss.http_server_evaulation_test.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServerRunner {

    public static final int PORT = 8082;

    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;

    public void run() throws InterruptedException {
        // Configure the server.
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
//                    .childHandler(new HelloWorldInitializer());
                    .childHandler(new GraphQLInitializer());

            ChannelFuture channelFuture = b.bind(PORT).sync();

            System.err.println("Started Netty server");

            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServerRunner().run();
    }
}
