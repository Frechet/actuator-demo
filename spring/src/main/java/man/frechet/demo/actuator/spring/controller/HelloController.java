package man.frechet.demo.actuator.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String hello() {
        return "Hello";
    }

    @RequestMapping(value = "/status", method = {RequestMethod.GET})
    public String status() {
        return "OK";
    }
}
