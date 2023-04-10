package one.edee.oss.http_server_evaulation_test.server;

import one.edee.oss.http_server_evaulation_test.server.undertow.UndertowServerRunner;

import java.util.Scanner;

public class RunUndertow {

    public static void main(String[] args) {
        // start undertow
        final UndertowServerRunner undertowServerRunner = new UndertowServerRunner();
        undertowServerRunner.run();

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Undertow server...");
            undertowServerRunner.stop();
        }));

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        System.exit(0);
    }
}
