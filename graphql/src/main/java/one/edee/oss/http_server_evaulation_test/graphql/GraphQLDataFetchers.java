package one.edee.oss.http_server_evaulation_test.graphql;

import graphql.schema.DataFetcher;

import java.util.Map;

public class GraphQLDataFetchers {

    public DataFetcher getEchoDataFetcher() {
        return env -> Map.of("message", env.getArgument("message"));
    }
}
