package one.edee.oss.http_server_evaulation_test.server.microhttp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLRequest;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLResponse;
import org.microhttp.Header;
import org.microhttp.Request;
import org.microhttp.Response;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GraphQLHandler extends AsyncHandler {

    private final GraphQLManager graphQLManager;
    private final ObjectMapper objectMapper;

    public GraphQLHandler() {
        this.graphQLManager = new GraphQLManager();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected Response doHandle(Request request) {
        if (!request.uri().equals("/graphql")) {
            return createResponse(404, "Not found", "".getBytes(StandardCharsets.UTF_8));
        }
        if (!request.method().equals("POST")) {
            return createResponse(400, "Only POST method is supported", "".getBytes(StandardCharsets.UTF_8));
        }

        // translate request
        final GraphQLRequest graphQLRequest;
        try {
            graphQLRequest = objectMapper.readValue(request.body(), GraphQLRequest.class);
        } catch (IOException e) {
            return createResponse(400, "Invalid body", "".getBytes(StandardCharsets.UTF_8));
        }

        final GraphQLResponse<Object> graphQLResponse = graphQLManager.execute(graphQLRequest);

        // send response
        final byte[] json;
        try {
            json = objectMapper.writeValueAsBytes(graphQLResponse);
        } catch (JsonProcessingException e) {
            return createResponse(500, "Json error", "".getBytes(StandardCharsets.UTF_8));
        }
        return createResponse(200, "OK", json);
    }

    private Response createResponse(int status, String Not_found, byte[] UTF_8) {
        return new Response(
                status,
                Not_found,
                List.of(new Header("Content-Type", "application/json")),
                UTF_8
        );
    }
}
