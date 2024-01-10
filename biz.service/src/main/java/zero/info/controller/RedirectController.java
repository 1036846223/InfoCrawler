package zero.info.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
public class RedirectController {

    private static final String baseUrl = "http://103.144.28.34:10000/";

    @RequestMapping("/test")
    public RedirectView redirectWithUsingRedirectTestView() {
        log.info("test,redirectSuc");
        return new RedirectView(baseUrl + "dataCraw1.html");
    }

    @RequestMapping("/")
    public RedirectView redirectWithUsingRedirectView() {
        log.info("stable,redirectSuc");
        return new RedirectView(baseUrl + "dataCraw.html");
    }
}
