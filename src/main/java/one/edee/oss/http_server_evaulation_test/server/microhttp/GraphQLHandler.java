package one.edee.oss.http_server_evaulation_test.server.microhttp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLProvider;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLRequest;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLResponse;
import org.microhttp.Header;
import org.microhttp.Request;
import org.microhttp.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class GraphQLHandler extends AsyncHandler {

    private final GraphQL graphQL;
    private final ObjectMapper objectMapper;

    public GraphQLHandler() {
        this.graphQL = new GraphQLProvider().getGraphQL();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected Response doHandle(Request request) {
        if (!request.uri().equals("/graphql")) {
            return new Response(
                    404,
                    "Not found",
                    List.of(new Header("Content-Type", "application/json")),
                    "".getBytes(StandardCharsets.UTF_8)
            );
        }
        if (!request.method().equals("POST")) {
            return new Response(
                    400,
                    "Only POST method is supported",
                    List.of(new Header("Content-Type", "application/json")),
                    "".getBytes(StandardCharsets.UTF_8)
            );
        }

        // translate request to execution input
        final GraphQLRequest graphQLRequest;
        try {
            graphQLRequest = objectMapper.readValue(request.body(), GraphQLRequest.class);
        } catch (IOException e) {
            return new Response(
                    400,
                    "Invalid body",
                    List.of(new Header("Content-Type", "application/json")),
                    "".getBytes(StandardCharsets.UTF_8)
            );
        }
        final ExecutionInput.Builder executionInputBuilder = new ExecutionInput.Builder()
                .query(graphQLRequest.query());
        if (graphQLRequest.operationName() != null) {
            executionInputBuilder.operationName(graphQLRequest.operationName());
        }
        if (graphQLRequest.variables() != null) {
            executionInputBuilder.variables(graphQLRequest.variables());
        }

        // execute query
        final ExecutionResult result = graphQL.execute(executionInputBuilder.build());

        // translate response
        final GraphQLResponse<Object> graphQLResponse = new GraphQLResponse<>(result.getData(), result.getErrors()
                .stream()
                .map(GraphQLError::getMessage)
                .collect(Collectors.toList()));

        // send response
        final byte[] json;
        try {
            json = objectMapper.writeValueAsBytes(graphQLResponse);
        } catch (JsonProcessingException e) {
            return new Response(
                    500,
                    "Json error",
                    List.of(new Header("Content-Type", "application/json")),
                    "".getBytes(StandardCharsets.UTF_8)
            );
        }
        return new Response(
                200,
                "OK",
                List.of(new Header("Content-Type", "application/json")),
                json
        );
    }
}
