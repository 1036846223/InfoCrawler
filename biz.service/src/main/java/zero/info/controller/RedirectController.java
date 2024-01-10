package zero.info.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {

    private static final String baseUrl = "http://103.144.28.34:10000/";

    @RequestMapping("/test")
    public RedirectView redirectWithUsingRedirectTestView() {
        return new RedirectView(baseUrl + "dataCraw1.html");
    }

    @RequestMapping("/")
    public RedirectView redirectWithUsingRedirectView() {
        return new RedirectView(baseUrl + "dataCraw.html");
    }
}
