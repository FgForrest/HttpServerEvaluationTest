package one.edee.oss.http_server_evaulation_test.server.nanohttpd;

import fi.iki.elonen.router.RouterNanoHTTPD;
import fi.iki.elonen.util.ServerRunner;

public class NanoHTTDServerRunner extends RouterNanoHTTPD {

    // thread pool example https://github.com/NanoHttpd/nanohttpd/wiki/Example:-Using-a-ThreadPool

    public NanoHTTDServerRunner() {
        super(4000);
        addMappings();
        System.out.println("Started NanoHTTPD server.");
    }

    @Override
    public void addMappings() {
        super.addMappings();
        addRoute("/", HelloWorldHandler.class);
        addRoute("/graphql", GraphQLHandler.class);
    }

    public static void main(String[] args) {
        ServerRunner.run(NanoHTTDServerRunner.class);
    }
}
