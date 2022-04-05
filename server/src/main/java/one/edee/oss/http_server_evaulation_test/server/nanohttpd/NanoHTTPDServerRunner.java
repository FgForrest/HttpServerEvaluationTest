package one.edee.oss.http_server_evaulation_test.server.nanohttpd;

import fi.iki.elonen.router.RouterNanoHTTPD;
import fi.iki.elonen.util.ServerRunner;

public class NanoHTTPDServerRunner extends RouterNanoHTTPD {

    public static final int PORT = 8083;

    // thread pool example https://github.com/NanoHttpd/nanohttpd/wiki/Example:-Using-a-ThreadPool

    public NanoHTTPDServerRunner() {
        super(PORT);
        addMappings();
        System.out.println("Started NanoHTTPD server.");
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

    public static void main(String[] args) {
        new NanoHTTPDServerRunner().run();
    }
}
