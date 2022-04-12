package one.edee.oss.http_server_evaulation_test.server.quarkus;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class HelloWorldResource {

    @GET
    public String helloWorld() {
        return "Hello World";
    }
}
