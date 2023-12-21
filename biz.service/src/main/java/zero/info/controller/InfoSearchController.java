package zero.info.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zero.info.enu.HttpResponseCodeEnum;
import zero.info.manager.InfoSearchManagerAsy;
import zero.info.request.InfoSearchRequest;
import zero.info.response.HttpResponse;

import javax.annotation.Resource;

/**
 * chaser
 */
@RequestMapping(value = "/info")
@Slf4j
@RestController
public class InfoSearchController {
    @Resource
    private InfoSearchManagerAsy infoSearchManagerAsy;

    @RequestMapping(value = "/single/search", method = RequestMethod.POST)
    public HttpResponse infoSingleSearch(@RequestBody InfoSearchRequest request) {
        try {
        } catch (Exception e) {
            log.error("anchorAccountSwitchSumbit error,req={}", request, e);
        }
        return HttpResponse.error(HttpResponseCodeEnum.SERVER_ERROR.getCode(), "操作失败");
    }
}
