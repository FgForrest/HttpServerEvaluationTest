package one.edee.oss.http_server_evaulation_test;

import one.edee.oss.http_server_evaulation_test.server.microhttp.MicroHTTPServerRunner;
import one.edee.oss.http_server_evaulation_test.server.nanohttpd.NanoHTTDServerRunner;
import one.edee.oss.http_server_evaulation_test.server.netty.NettyServerRunner;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class RunServers {

    public static void main(String[] args) {
        System.out.println("Starting servers...");

        // start microhttp
        final MicroHTTPServerRunner microHTTPServerRunner = new MicroHTTPServerRunner();
        final Thread microHTTPServerThread = new Thread(() -> {
            try {
                microHTTPServerRunner.run();
            } catch (IOException e) {
                // end
            }
            System.out.println("Stopped microhttp server.");
        });
        microHTTPServerThread.setDaemon(true);
        microHTTPServerThread.start();

        // start netty
        final NettyServerRunner nettyServerRunner = new NettyServerRunner();
        final Thread nettyServerThread = new Thread(() -> {
            try {
                nettyServerRunner.run();
            } catch (InterruptedException e) {
                // end
            }
            System.out.println("Stopped netty server.");
        });
        nettyServerThread.setDaemon(true);
        nettyServerThread.start();

        // start nanohttpd
        final NanoHTTDServerRunner nanoHTTPDServerRunner = new NanoHTTDServerRunner();
        final Thread nanoHTTPDServerThread = new Thread(() -> {
            nanoHTTPDServerRunner.run();
            System.out.println("Stopped nanohttpd server.");
        });
        nanoHTTPDServerThread.setDaemon(true);
        nanoHTTPDServerThread.start();

        // shutdown hook
        AtomicBoolean running = new AtomicBoolean(true);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down servers...");

            microHTTPServerRunner.stop();
            nettyServerRunner.stop();
            nanoHTTPDServerRunner.stop();

            try {
                // let servers shutdown
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                // do nothing
            }

            running.set(false);
        }));

        while (running.get()) {
            // running
        }
        System.out.println("Servers are shutdown.");
    }
}