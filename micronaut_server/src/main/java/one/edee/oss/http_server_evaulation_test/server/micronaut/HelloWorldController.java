package one.edee.oss.http_server_evaulation_test.server.micronaut;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/") //
public class HelloWorldController {

    @Get(produces = MediaType.TEXT_PLAIN) //
    public String index() {
        return "Hello World"; //
    }
}