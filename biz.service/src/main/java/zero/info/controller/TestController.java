package zero.info.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autoTest")
public class TestController {

    @GetMapping("/hello")
    public String index() {
        return "Hello World!" +
                "your process success";
    }
}