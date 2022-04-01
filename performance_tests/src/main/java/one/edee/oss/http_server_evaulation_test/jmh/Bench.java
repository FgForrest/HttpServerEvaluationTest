package one.edee.oss.http_server_evaulation_test.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.TimeUnit;

public class Bench {

    @BenchmarkMode(Mode.Throughput)
    @Benchmark
    @Measurement(time = 1, timeUnit = TimeUnit.MINUTES)
    @Threads(Threads.MAX)
    public void graphQLApiEchoQuery_MicroHTTPServer(MicroHTTPServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        blackhole.consume(state.client.send(state.request, BodyHandlers.ofString()));
    }

}
