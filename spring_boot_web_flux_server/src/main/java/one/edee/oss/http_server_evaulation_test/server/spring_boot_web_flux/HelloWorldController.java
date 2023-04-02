package one.edee.oss.http_server_evaulation_test.server.spring_boot_web_flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/")
public class HelloWorldController {

    @GetMapping
    public Mono<String> helloWorld() {
        return Mono.just("Hello world");
    }

}
