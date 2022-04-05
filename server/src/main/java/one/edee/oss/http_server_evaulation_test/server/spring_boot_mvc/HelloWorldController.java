package one.edee.oss.http_server_evaulation_test.server.spring_boot_mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class HelloWorldController {

    @GetMapping
    public String helloWorld() {
        return "Hello world";
    }
}
