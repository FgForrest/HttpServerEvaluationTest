package one.edee.oss.http_server_evaulation_test.server.spring_boot_mvc;

import one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLRequest;
import one.edee.oss.http_server_evaulation_test.graphql.GraphQLResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/graphql")
public class GraphQLController {

    private final GraphQLManager graphQLManager;

    public GraphQLController(GraphQLManager graphQLManager) {
        this.graphQLManager = graphQLManager;
    }

    @PostMapping
    public <T> GraphQLResponse<T> query(@RequestBody GraphQLRequest request) {
        return graphQLManager.execute(request);
    }
}
