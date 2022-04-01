package one.edee.oss.http_server_evaulation_test.jmh;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ServersLatencyBenchmark extends ServersBenchmark {

    @Override
    public void graphQLApiEchoQuery_MicroHTTPServer(MicroHTTPServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        super.graphQLApiEchoQuery_MicroHTTPServer(state, blackhole);
    }
}
