package one.edee.oss.http_server_evaulation_test.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import static java.util.Optional.ofNullable;

public class NettyServerRunner {

    public static final int PORT = 8082;

    EventLoopGroup bossGroup;
    EventLoopGroup workerGroup;

    public void run() throws InterruptedException {
        // Configure the server.
        bossGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors());
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new DispatcherInitializer());

            final int port = ofNullable(System.getProperty("port")).map(Integer::parseInt).orElse(PORT);;
            ChannelFuture channelFuture = b.bind(port).sync();

            System.err.println("Started Netty server on port " + port + ".");

            channelFuture.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        System.out.println("Netty server stopped.");
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyServerRunner().run();
    }
}
