package one.edee.oss.http_server_evaulation_test.jmh;

import org.openjdk.jmh.annotations.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

@State(Scope.Thread)
public abstract class ServerState {

    public HttpClient client;
    public HttpRequest request;

    @Setup(Level.Trial)
    public void setUp() throws URISyntaxException {
        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        /*
        // requests following graphql query
        query Echo {
            echo(message: "hej") {
                message
            }
        }
         */
        request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + getServerPort() + "/graphql"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        """
                        {
                          "query": "query Echo {\\n\\techo(message: \\"hello\\") {\\n\\t\\tmessage\\n\\t}\\n}",
                          "operationName": "Echo"
                        }"""
                ))
                .build();
    }

    @TearDown(Level.Trial)
    public void tearDown() {
        client = null;
    }

    protected abstract int getServerPort();
}
