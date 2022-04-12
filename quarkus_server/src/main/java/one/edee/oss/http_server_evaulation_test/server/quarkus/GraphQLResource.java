package one.edee.oss.http_server_evaulation_test.server.quarkus;

import one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLRequest;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLResponse;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/graphql")
public class GraphQLResource {

    private final GraphQLManager graphQLManager;

    public GraphQLResource() {
        this.graphQLManager = new GraphQLManager();
    }

    @POST
    public GraphQLResponse graphQL(GraphQLRequest request) {
        return graphQLManager.execute(request);
    }
}
