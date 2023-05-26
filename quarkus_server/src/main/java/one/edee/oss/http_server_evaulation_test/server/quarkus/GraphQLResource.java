package one.edee.oss.http_server_evaulation_test.server.quarkus;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@Path("/graphql")
@GraphQLApi
public class GraphQLResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Query
    public EchoResponse echo(String message) {
        return new EchoResponse(message);
    }

    public static class EchoResponse {
        private final String message;

        public EchoResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
