package one.edee.oss.http_server_evaulation_test;

import one.edee.oss.http_server_evaulation_test.server.javalin.JavalinServerRunner;
import one.edee.oss.http_server_evaulation_test.server.microhttp.MicroHTTPServerRunner;
import one.edee.oss.http_server_evaulation_test.server.nanohttpd.NanoHTTPDServerRunner;
import one.edee.oss.http_server_evaulation_test.server.netty.NettyServerRunner;
import one.edee.oss.http_server_evaulation_test.server.undertow.UndertowServerRunner;
import one.edee.oss.http_server_evaulation_test.server.vertx.VertXServerRunner;

import java.io.IOException;
import java.util.Scanner;
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
        });
        nettyServerThread.setDaemon(true);
        nettyServerThread.start();

        // start nanohttpd
        final NanoHTTPDServerRunner nanoHTTPDServerRunner = new NanoHTTPDServerRunner();
        final Thread nanoHTTPDServerThread = new Thread(nanoHTTPDServerRunner::run);
        nanoHTTPDServerThread.setDaemon(true);
        nanoHTTPDServerThread.start();

        // start javalin
        final JavalinServerRunner javalinServerRunner = new JavalinServerRunner();
        javalinServerRunner.run();

        // start vertx
        final VertXServerRunner vertXServerRunner = new VertXServerRunner();
        vertXServerRunner.run();

        // start spring boot mvc
        System.out.println("Run Spring boot MVC separately!!!");

        // start spring boot webflux
        System.out.println("Run Spring Boot WebFlux separately!!!");

        // start undertow
        final UndertowServerRunner undertowServerRunner = new UndertowServerRunner();
        undertowServerRunner.run();

        // shutdown hook
        AtomicBoolean running = new AtomicBoolean(true);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down servers...");

            microHTTPServerRunner.stop();
            nettyServerRunner.stop();
            nanoHTTPDServerRunner.stop();
            javalinServerRunner.stop();
            vertXServerRunner.stop();
            undertowServerRunner.stop();

            try {
                // let servers shutdown
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                // do nothing
            }

            running.set(false);
        }));

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        System.exit(0);
        System.out.println("Servers are shutdown.");
    }
}
