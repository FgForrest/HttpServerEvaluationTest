package one.edee.oss.http_server_evaulation_test.server.undertow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLRequest;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class GraphQLHandler implements HttpHandler {

    private final GraphQLManager graphQLManager;
    private final ObjectMapper objectMapper;

    public GraphQLHandler() {
        this.graphQLManager = new GraphQLManager();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        if (!exchange.getRequestMethod().equals(HttpString.tryFromString("POST"))) {
            setResponse(exchange, 400, "");
            return;
        }

        final String body;
        try (final InputStream is = exchange.getInputStream();
             final InputStreamReader isr = new InputStreamReader(is);
             final BufferedReader bf = new BufferedReader(isr)) {
            body = bf.lines().collect(Collectors.joining("\n"));
        }

        // translate request
        final GraphQLRequest graphQLRequest;
        try {
            graphQLRequest = objectMapper.readValue(body, GraphQLRequest.class);
        } catch (IOException e) {
            setResponse(exchange, 400, "Invalid body");
            return;
        }

        final GraphQLResponse<Object> graphQLResponse = graphQLManager.execute(graphQLRequest);

        // send response
        final String json;
        try {
            json = objectMapper.writeValueAsString(graphQLResponse);
        } catch (JsonProcessingException e) {
            setResponse(exchange, 500, "Json error");
            return;
        }

        setResponse(exchange, 200, json);
    }

    private void setResponse(HttpServerExchange exchange, int statusCode, String data) {
        exchange.setStatusCode(statusCode);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send(data);
    }
}
