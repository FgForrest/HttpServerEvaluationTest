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

    @Override
    public void graphQLApiEchoQuery_NettyServer(NettyServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        super.graphQLApiEchoQuery_NettyServer(state, blackhole);
    }

    @Override
    public void graphQLApiEchoQuery_NanoHTTPDServer(NanoHTTPDServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        super.graphQLApiEchoQuery_NanoHTTPDServer(state, blackhole);
    }

    @Override
    public void graphQLApiEchoQuery_JavalinServer(JavalinServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        super.graphQLApiEchoQuery_JavalinServer(state, blackhole);
    }

    @Override
    public void graphQLApiEchoQuery_VertXServer(VertXServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        super.graphQLApiEchoQuery_VertXServer(state, blackhole);
    }

    @Override
    public void graphQLApiEchoQuery_SpringBootMVCServer(SpringBootMVCServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        super.graphQLApiEchoQuery_SpringBootMVCServer(state, blackhole);
    }

    @Override
    public void graphQLApiEchoQuery_SpringBootWebFluxServer(SpringBootWebFluxServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        super.graphQLApiEchoQuery_SpringBootWebFluxServer(state, blackhole);
    }

    @Override
    public void graphQLApiEchoQuery_UndertowServer(UndertowServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        super.graphQLApiEchoQuery_UndertowServer(state, blackhole);
    }

    @Override
    public void graphQLApiEchoQuery_QuarkusServer(QuarkusServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        super.graphQLApiEchoQuery_QuarkusServer(state, blackhole);
    }

    @Override
    public void graphQLApiEchoQuery_MicronautServer(MicronautServerState state, Blackhole blackhole) throws IOException, InterruptedException {
        super.graphQLApiEchoQuery_MicronautServer(state, blackhole);
    }
}
