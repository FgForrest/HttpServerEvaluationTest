package one.edee.oss.http_server_evaulation_test.server.micronaut;

import io.micronaut.runtime.Micronaut;

public class MicronautServerRunner {

    public static void main(String[] args) {
        Micronaut.run(MicronautServerRunner.class, args);
    }
}
