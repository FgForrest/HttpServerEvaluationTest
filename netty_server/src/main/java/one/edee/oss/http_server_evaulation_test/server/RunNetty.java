package one.edee.oss.http_server_evaulation_test.server;

import one.edee.oss.http_server_evaulation_test.server.netty.NettyServerRunner;

import java.util.Scanner;

public class RunNetty {

    public static void main(String[] args) {
        // start netty
        final NettyServerRunner nettyServerRunner = new NettyServerRunner();
        final Thread nettyServerThread = new Thread(() -> {
            try {
                nettyServerRunner.run();
            } catch (InterruptedException e) {
                // end
            }
        });
        nettyServerThread.setDaemon(true);
        nettyServerThread.start();

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Netty server...");
            nettyServerRunner.stop();
        }));

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        System.exit(0);
    }
}
