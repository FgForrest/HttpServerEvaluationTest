package one.edee.oss.http_server_evaulation_test.server.micronaut;

import graphql.GraphQL;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLProvider;

@Factory
public class GraphQLFactory {

    @Bean
    @Singleton
    public GraphQL graphQL() {
        return new GraphQLProvider().getGraphQL();
    }
}
