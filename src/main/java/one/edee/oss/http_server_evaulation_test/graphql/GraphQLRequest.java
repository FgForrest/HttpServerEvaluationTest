package one.edee.oss.http_server_evaulation_test.graphql;

import java.util.Map;

public record GraphQLRequest(String query,
                             String operationName,
                             Map<String, Object> variables) {}
