package one.edee.oss.http_server_evaulation_test.graphql;

import java.util.List;

public record GraphQLResponse<T>(T data,
                                 List<String> errors) {
}
