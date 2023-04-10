package one.edee.oss.http_server_evaulation_test.server.nanohttpd;

import fi.iki.elonen.router.RouterNanoHTTPD;
import fi.iki.elonen.util.ServerRunner;

import static java.util.Optional.ofNullable;

public class NanoHTTPDServerRunner extends RouterNanoHTTPD {

    public static final int PORT = 8083;

    // thread pool example https://github.com/NanoHttpd/nanohttpd/wiki/Example:-Using-a-ThreadPool

    public NanoHTTPDServerRunner() {
        super(ofNullable(System.getProperty("port")).map(Integer::parseInt).orElse(PORT));
        addMappings();
        System.out.println("Started NanoHTTPD server on port " + ofNullable(System.getProperty("port")).map(Integer::parseInt).orElse(PORT) + ".");
    }

    @Override
    public void addMappings() {
        super.addMappings();
        addRoute("/", HelloWorldHandler.class);
        addRoute("/graphql", GraphQLHandler.class);
    }

    public void run() {
        ServerRunner.executeInstance(this);
    }

    @Override
    public void stop() {
        super.stop();
        System.out.println("Nanohttpd server stopped.");
    }

    public static void main(String[] args) {
        new NanoHTTPDServerRunner().run();
    }
}
