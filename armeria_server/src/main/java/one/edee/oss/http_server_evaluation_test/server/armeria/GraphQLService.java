package one.edee.oss.http_server_evaluation_test.server.armeria;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linecorp.armeria.common.*;
import com.linecorp.armeria.server.ServiceRequestContext;
import com.linecorp.armeria.server.annotation.Blocking;
import com.linecorp.armeria.server.annotation.Post;
import com.linecorp.armeria.server.annotation.RequestConverter;
import com.linecorp.armeria.server.annotation.StringRequestConverterFunction;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLRequest;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLResponse;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class GraphQLService {
    private final GraphQLManager graphQLManager;
    private final ObjectMapper objectMapper;

    public GraphQLService() {
        this.graphQLManager = new GraphQLManager();
        this.objectMapper = new ObjectMapper();
    }

    @Blocking
    @Post("/graphql")
    @RequestConverter(StringRequestConverterFunction.class)
    public ResponseEntity<String> serve(ServiceRequestContext ctx, AggregatedHttpRequest req) throws ExecutionException, InterruptedException {
        if (!req.method().equals(HttpMethod.POST)) {
            final ResponseHeaders headers =
                    ResponseHeaders.builder(400)
                            .contentType(MediaType.JSON)
                            .build();
            return ResponseEntity.of(headers, "");
        }

        final AtomicReference<String> bodyRef = new AtomicReference<>();
        try (HttpData data = req.content()) {
            bodyRef.set(data.toStringUtf8());
        }

        final String body = bodyRef.get();

        // translate request
        final GraphQLRequest graphQLRequest;
        try {
            graphQLRequest = objectMapper.readValue(body, GraphQLRequest.class);
        } catch (IOException e) {
            final ResponseHeaders headers =
                    ResponseHeaders.builder(400)
                            .contentType(MediaType.JSON)
                            .build();
            return ResponseEntity.of(headers, "Invalid body");
        }

        final GraphQLResponse<Object> graphQLResponse = graphQLManager.execute(graphQLRequest);

        // send response
        final String json;
        try {
            json = objectMapper.writeValueAsString(graphQLResponse);
        } catch (JsonProcessingException e) {
            final ResponseHeaders headers =
                    ResponseHeaders.builder(500)
                            .contentType(MediaType.JSON)
                            .build();
            return ResponseEntity.of(headers, "Json error");
        }

        final ResponseHeaders headers =
                ResponseHeaders.builder(200)
                        .contentType(MediaType.JSON)
                        .build();
        return ResponseEntity.of(headers, json);
    }

    private HttpResponse createResponse(int statusCode, String data) {
        return HttpResponse.builder()
                .status(statusCode)
                .header("Content-Type", "application/json")
                .content(data)
                .build();
    }
}
