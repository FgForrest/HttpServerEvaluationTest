package one.edee.oss.http_server_evaulation_test.server.microhttp;

import org.microhttp.EventLoop;
import org.microhttp.LogEntry;
import org.microhttp.Logger;
import org.microhttp.Options;

import java.io.IOException;

public class MicroHTTPServerRunner {

    private EventLoop eventLoop;

    public void run() throws IOException {
        Options options = new Options()
                .withHost("localhost")
                .withPort(8081);

//        eventLoop = new EventLoop(options, new NoOpLogger(), new HelloWorldHandler());
        eventLoop = new EventLoop(options, new NoOpLogger(), new GraphQLHandler());
        System.out.println("Starting microhttp server...");
        eventLoop.start();
    }

    public void stop() {
        eventLoop.stop();
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