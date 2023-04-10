package one.edee.oss.http_server_evaulation_test.server;

import one.edee.oss.http_server_evaulation_test.server.javalin.JavalinServerRunner;

import java.util.Scanner;

public class RunJavalin {

    public static void main(String[] args) {
        System.out.println("Starting Javalin server...");

        // start javalin
        final JavalinServerRunner javalinServerRunner = new JavalinServerRunner();
        javalinServerRunner.run();

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Javalin server...");
            javalinServerRunner.stop();
        }));

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        System.exit(0);
        System.out.println("Javalin server are shutdown.");
    }
}
