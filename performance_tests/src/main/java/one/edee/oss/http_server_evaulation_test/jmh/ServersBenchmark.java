package one.edee.oss.http_server_evaulation_test.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.TimeUnit;

public abstract class ServersBenchmark {

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.MINUTES)
    @Threads(Threads.MAX)
    public void graphQLApiEchoQuery_MicroHTTPServer(MicroHTTPServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        blackhole.consume(callGraphQLApi(state));
    }

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.MINUTES)
    @Threads(Threads.MAX)
    public void graphQLApiEchoQuery_NettyServer(NettyServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        blackhole.consume(callGraphQLApi(state));
    }

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.MINUTES)
    @Threads(Threads.MAX)
    public void graphQLApiEchoQuery_NanoHTTPDServer(NanoHTTPDServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        blackhole.consume(callGraphQLApi(state));
    }

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.MINUTES)
    @Threads(Threads.MAX)
    public void graphQLApiEchoQuery_JavalinServer(JavalinServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        blackhole.consume(callGraphQLApi(state));
    }

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.MINUTES)
    @Threads(Threads.MAX)
    public void graphQLApiEchoQuery_VertXServer(VertXServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        blackhole.consume(callGraphQLApi(state));
    }

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.MINUTES)
    @Threads(Threads.MAX)
    public void graphQLApiEchoQuery_SpringBootMVCServer(SpringBootMVCServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        blackhole.consume(callGraphQLApi(state));
    }

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.MINUTES)
    @Threads(Threads.MAX)
    public void graphQLApiEchoQuery_SpringBootWebFluxServer(SpringBootWebFluxServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        blackhole.consume(callGraphQLApi(state));
    }

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.MINUTES)
    @Threads(Threads.MAX)
    public void graphQLApiEchoQuery_UndertowServer(UndertowServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        blackhole.consume(callGraphQLApi(state));
    }

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.MINUTES)
    @Threads(Threads.MAX)
    public void graphQLApiEchoQuery_QuarkusServer(QuarkusServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        blackhole.consume(callGraphQLApi(state));
    }

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.MINUTES)
    @Threads(Threads.MAX)
    public void graphQLApiEchoQuery_MicronautServer(MicronautServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        blackhole.consume(callGraphQLApi(state));
    }

    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.MINUTES)
    @Threads(Threads.MAX)
    public void graphQLApiEchoQuery_ArmeriaServer(ArmeriaServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        blackhole.consume(callGraphQLApi(state));
    }

    private HttpResponse<String> callGraphQLApi(ServerState state) throws IOException, InterruptedException {
        IOException ex = null;
        for (int i = 0; i < 5; i++) {
            try {
                return state.client.send(state.request, BodyHandlers.ofString());
            } catch (IOException e) {
                // remember exception and just retry
                ex = e;
            }
        }
        // retry failed 5 times
        throw ex;
    }
}
