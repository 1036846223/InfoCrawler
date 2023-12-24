package zero.info.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * chaser
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/hello")
    public String index() {
        return "Hello World!";
    }
}