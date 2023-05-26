package one.edee.oss.http_server_evaulation_test.server.quarkus;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class QuarkusServerRunner {

    public static final int PORT = 8089;

    public static void main(String... args) {
        Quarkus.run(QuarkusServer.class, args);
    }

    public static class QuarkusServer implements QuarkusApplication {

        @Override
        public int run(String... args) {
            System.out.println("Quarkus server started.");
            Quarkus.waitForExit();
            System.out.println("Quarkus server stopped.");
            return 0;
        }
    }
}
