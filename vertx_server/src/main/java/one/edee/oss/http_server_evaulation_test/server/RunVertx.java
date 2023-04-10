package one.edee.oss.http_server_evaulation_test.server;

import one.edee.oss.http_server_evaulation_test.server.vertx.VertXServerRunner;

import java.util.Scanner;

public class RunVertx {

    public static void main(String[] args) {
        // start vertx
        final VertXServerRunner vertXServerRunner = new VertXServerRunner();
        vertXServerRunner.run();

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Vertx server...");
            vertXServerRunner.stop();
        }));

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        System.exit(0);
    }
}
