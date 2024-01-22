package zero.info.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@Slf4j
public class RedirectController {

        private static final String baseUrl = "http://www.jujue.icu/";
//    private static final String baseUrl = "http://localhost:10000/"; // 测试域名

    @RequestMapping("/test")
    public RedirectView redirectWithUsingRedirectTestView() {
        log.info("test,redirectSuc");
        return new RedirectView(baseUrl + "dataCraw1.html");
    }

    @RequestMapping("/menu")
    public RedirectView redirectWithUsingRedirectView() {
        log.info("stable,redirectSuc");
        return new RedirectView(baseUrl + "dataCraw.html");
    }
}
