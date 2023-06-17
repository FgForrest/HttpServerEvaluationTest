package one.edee.oss.http_server_evaulation_test.server;

import fi.iki.elonen.NanoHTTPD;
import one.edee.oss.http_server_evaulation_test.server.nanohttpd.NanoHTTPDServerRunner;

import java.io.IOException;
import java.util.Scanner;

public class RunNanohttpd {

    public static void main(String[] args) throws IOException {
        // start nanohttpd
        final NanoHTTPDServerRunner nanoHTTPDServerRunner = new NanoHTTPDServerRunner();
        nanoHTTPDServerRunner.start(NanoHTTPD.SOCKET_READ_TIMEOUT, true);

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
