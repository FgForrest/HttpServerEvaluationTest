package one.edee.oss.http_server_evaulation_test.server.microhttp;

import org.microhttp.EventLoop;
import org.microhttp.LogEntry;
import org.microhttp.Logger;
import org.microhttp.Options;

import java.io.IOException;

public class MicroHTTPServerRunner {

    public static final int PORT = 8081;

    private EventLoop eventLoop;

    public void run() throws IOException {
        Options options = new Options()
                .withHost("localhost")
                .withPort(PORT);

        eventLoop = new EventLoop(options, new NoOpLogger(), new DispatcherHandler());
        System.out.println("Starting microhttp server...");
        eventLoop.start();
    }

    public void stop() {
        eventLoop.stop();
        System.out.println("Microhttp server stopped.");
    }

    public static void main(String[] args) throws IOException {
        new MicroHTTPServerRunner().run();
    }

    static class NoOpLogger implements Logger {
        @Override
        public boolean enabled() {
            return false;
        }

        @Override
        public void log(LogEntry... entries) {

        }

        @Override
        public void log(Exception e, LogEntry... entries) {

        }
    }
}
