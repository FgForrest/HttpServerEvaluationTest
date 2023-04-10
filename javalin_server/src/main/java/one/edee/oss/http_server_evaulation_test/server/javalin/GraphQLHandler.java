package one.edee.oss.http_server_evaulation_test.server.javalin;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLRequest;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLResponse;
import org.jetbrains.annotations.NotNull;

public class GraphQLHandler implements Handler {

    private GraphQLManager graphQLManager;

    public GraphQLHandler() {
        this.graphQLManager = new GraphQLManager();
    }

    @Override
    public void handle(@NotNull Context ctx) throws Exception {
        final GraphQLRequest graphQLRequest = ctx.bodyAsClass(GraphQLRequest.class);
        final GraphQLResponse<Object> graphQLResponse = graphQLManager.execute(graphQLRequest);

        ctx.status(HttpStatus.OK);
        ctx.json(graphQLResponse);
    }
}
