package one.edee.oss.http_server_evaulation_test.server.quarkus;

import io.smallrye.common.annotation.NonBlocking;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class HelloWorldResource {

    @GET
    @NonBlocking
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "Hello World";
    }
}
