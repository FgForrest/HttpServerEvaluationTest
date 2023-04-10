package one.edee.oss.http_server_evaulation_test.server;

import one.edee.oss.http_server_evaulation_test.server.nanohttpd.NanoHTTPDServerRunner;

import java.util.Scanner;

public class RunNanohttpd {

    public static void main(String[] args) {
        // start nanohttpd
        final NanoHTTPDServerRunner nanoHTTPDServerRunner = new NanoHTTPDServerRunner();
        final Thread nanoHTTPDServerThread = new Thread(nanoHTTPDServerRunner::run);
        nanoHTTPDServerThread.setDaemon(true);
        nanoHTTPDServerThread.start();

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down Nanohttpd server...");
            nanoHTTPDServerRunner.stop();
        }));

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        System.exit(0);
    }
}
