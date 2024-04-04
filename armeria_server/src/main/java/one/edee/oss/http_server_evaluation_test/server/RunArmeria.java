package one.edee.oss.http_server_evaluation_test.server;

import one.edee.oss.http_server_evaluation_test.server.armeria.ArmeriaServerRunner;

import java.util.Scanner;

public class RunArmeria {
    public static void main(String[] args) {
        // start armeria
        final ArmeriaServerRunner armeriaServerRunner = new ArmeriaServerRunner();
        armeriaServerRunner.run();

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Armeria server...");
            armeriaServerRunner.stop();
        }));

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        System.exit(0);
    }
}
