package one.edee.oss.http_server_evaulation_test.server.quarkus;

import io.smallrye.common.annotation.NonBlocking;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLRequest;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLResponse;

@Path("/graphql")
public class GraphQLResource {

    private final GraphQLManager graphQLManager;

    public GraphQLResource() {
        this.graphQLManager = new GraphQLManager();
    }

    @POST
    @NonBlocking
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<GraphQLResponse<Object>> graphQL(GraphQLRequest request) {
        return Uni.createFrom().completionStage(graphQLManager.executeAsync(request));
    }
}
