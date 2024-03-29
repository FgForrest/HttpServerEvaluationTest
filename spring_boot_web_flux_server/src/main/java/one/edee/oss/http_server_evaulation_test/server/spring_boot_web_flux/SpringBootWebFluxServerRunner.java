package one.edee.oss.http_server_evaulation_test.server.spring_boot_web_flux;

import one.edee.oss.http_server_evaulation_test.graphql.GraphQLManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

import static java.util.Optional.ofNullable;

/**
 * NOTE: has to be run separately because it cannot be easily embeddable like other servers
 */
@SpringBootApplication
public class SpringBootWebFluxServerRunner {

    public static final int PORT = 8087;

    @Bean
    public GraphQLManager graphQLManager() {
        return new GraphQLManager();
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringBootWebFluxServerRunner.class);
        final int port = ofNullable(System.getProperty("port")).map(Integer::parseInt).orElse(PORT);;
        app.setDefaultProperties(Collections.singletonMap("server.port", String.valueOf(port)));
        app.run(args);
    }
}
