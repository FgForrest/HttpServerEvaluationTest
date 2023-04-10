package one.edee.oss.http_server_evaulation_test.server;

import one.edee.oss.http_server_evaulation_test.server.microhttp.MicroHTTPServerRunner;

import java.io.IOException;
import java.util.Scanner;

public class RunMicrohttp {

    public static void main(String[] args) {
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

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Microhttp server...");
            microHTTPServerRunner.stop();
        }));

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        System.exit(0);
    }
}
