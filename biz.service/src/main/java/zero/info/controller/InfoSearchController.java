package zero.info.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import zero.info.dto.UrlContentDTO;
import zero.info.enu.HttpResponseCodeEnum;
import zero.info.manager.InfoSearchManagerAsy;
import zero.info.manager.InfoSearchManagerSyn;
import zero.info.request.InfoSearchRequest;
import zero.info.response.HttpResponse;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@RequestMapping(value = "/info")
@Slf4j
@RestController
public class InfoSearchController {
    @Resource
    private InfoSearchManagerAsy infoSearchManagerAsy;
    @Resource
    private InfoSearchManagerSyn infoSearchManagerSyn;

    @RequestMapping(value = "/single/search/str", method = RequestMethod.GET)
    public HttpResponse<String> infoSingleSearchToStr(@RequestParam("url") String url) {
        try {
            if (StringUtils.isEmpty(url)) {
                return HttpResponse.error(HttpResponseCodeEnum.PARAM_ERROR.getCode(), "入参为空");
            }
            InfoSearchRequest request = new InfoSearchRequest();
            request.setUrlList(Collections.singletonList(url));
            HttpResponse<String> response = infoSearchManagerSyn.singleInfoSearchToStr(request);
            if (response != null) {
                return response;
            }
        } catch (Exception e) {
            log.error("infoSingleSearch_error,url={}", url, e);
        }
        return HttpResponse.error(HttpResponseCodeEnum.SERVER_ERROR.getCode(), "操作失败");
    }

    @RequestMapping(value = "/single/search", method = RequestMethod.POST)
    public HttpResponse<List<UrlContentDTO>> infoSingleSearch(@RequestBody InfoSearchRequest request) {
        try {
            HttpResponse<List<UrlContentDTO>> response = infoSearchManagerSyn.singleInfoSearch(request);
            if (response != null) {
                return response;
            }
        } catch (Exception e) {
            log.error("infoSingleSearch_error,req={}", request, e);
        }
        return HttpResponse.error(HttpResponseCodeEnum.SERVER_ERROR.getCode(), "操作失败");
    }


    @RequestMapping(value = "/single/search/save", method = RequestMethod.POST)
    public HttpResponse<List<UrlContentDTO>> infoSingleSearchAndSave(@RequestBody InfoSearchRequest request) {
        try {
            HttpResponse<List<UrlContentDTO>> response = infoSearchManagerSyn.singleInfoSearchAndSave(request);
            if (response != null) {
                return response;
            }
        } catch (Exception e) {
            log.error("infoSingleSearchAndSave_error,req={}", request, e);
        }
        return HttpResponse.error(HttpResponseCodeEnum.SERVER_ERROR.getCode(), "操作失败");
    }

    @RequestMapping(value = "/batch/search/save", method = RequestMethod.POST)
    public HttpResponse<Boolean> infoBatchSearchAndSave(@RequestBody InfoSearchRequest request) {
        try {
            HttpResponse<Boolean> response = infoSearchManagerAsy.batchInfoSearchAndSave(request);
            if (response != null) {
                return response;
            }
        } catch (Exception e) {
            log.error("infoSingleSearchAndSave_error,req={}", request, e);
        }
        return HttpResponse.error(HttpResponseCodeEnum.SERVER_ERROR.getCode(), "操作失败");
    }
}
