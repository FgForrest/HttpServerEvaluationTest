package one.edee.oss.http_server_evaulation_test.graphql;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;

import java.util.stream.Collectors;

public class GraphQLManager {

    private final GraphQL graphQL;

    public GraphQLManager() {
        this.graphQL = new GraphQLProvider().getGraphQL();
    }

    public <T> GraphQLResponse<T> execute(GraphQLRequest request) {
        final ExecutionInput.Builder executionInputBuilder = new ExecutionInput.Builder()
                .query(request.query());
        if (request.operationName() != null) {
            executionInputBuilder.operationName(request.operationName());
        }
        if (request.variables() != null) {
            executionInputBuilder.variables(request.variables());
        }

        // execute query
        final ExecutionResult result = graphQL.execute(executionInputBuilder.build());

        // translate response
        return new GraphQLResponse<>(result.getData(), result.getErrors()
                .stream()
                .map(GraphQLError::getMessage)
                .collect(Collectors.toList()));
    }
}
