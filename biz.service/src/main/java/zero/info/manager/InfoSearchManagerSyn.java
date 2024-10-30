package zero.info.manager;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import zero.info.dto.OriginContent;
import zero.info.dto.UrlContentDTO;
import zero.info.item.ResultItems;
import zero.info.item.Spider;
import zero.info.processor.example.WeChatPublicAccountProcessor;
import zero.info.request.InfoSearchRequest;
import zero.info.response.HttpResponse;
import zero.info.service.ZhihuCrawlerServiceV1;
import zero.info.service.ZhihuPipeline;
import zero.info.service.ZhihuPipelineV1;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 同步,提供单个接口查询及保存服务
 */
@Service
@Slf4j
public class InfoSearchManagerSyn {
    @Resource
    private InfoSearchManagerMeta infoSearchManagerMeta;

    private static final String CLAZZ_TYPE = InfoSearchManagerSyn.class.getSimpleName();


    public HttpResponse<String> singleInfoSearchToStr(InfoSearchRequest request) {
        log.info("singleInfoSearch,request={}", JSON.toJSONString(request));
        if (request == null || CollectionUtils.isEmpty(request.getUrlList())) {
            log.info("singleInfoSearch,requestNull,request={}", JSON.toJSONString(request));
            return null;
        }
        try {
            List<UrlContentDTO> urlContentDTOS = infoSearchManagerMeta.singleInfoSearch(request);
            if (!CollectionUtils.isEmpty(urlContentDTOS)) {
                StringBuilder stb = new StringBuilder();
                for (UrlContentDTO urlContentDTO : urlContentDTOS) {
                    stb.append(urlContentDTO.getContent()).append("/n");
                }
                if (StringUtils.isNotEmpty(stb.toString())) {
                    return HttpResponse.success(stb.toString());
                }
            }
        } catch (Exception e) {
            log.error("singleInfoSearch_error,request={}", JSON.toJSONString(request), e);
        }
        log.info("singleInfoSearch_error,request={}", JSON.toJSONString(request));
        return null;
    }

    public HttpResponse<List<UrlContentDTO>> singleInfoSearch(InfoSearchRequest request) {
        log.info("singleInfoSearch,request={}", JSON.toJSONString(request));
        if (request == null || CollectionUtils.isEmpty(request.getUrlList())) {
            log.info("singleInfoSearch,requestNull,request={}", JSON.toJSONString(request));
            return null;
        }
        try {
            List<UrlContentDTO> urlContentDTOS = infoSearchManagerMeta.singleInfoSearch(request);
            if (!CollectionUtils.isEmpty(urlContentDTOS)) {
                return HttpResponse.success(urlContentDTOS);
            }
        } catch (Exception e) {
            log.error("singleInfoSearch_error,request={}", JSON.toJSONString(request), e);
        }
        log.info("singleInfoSearch_error,request={}", JSON.toJSONString(request));
        return null;
    }

    public HttpResponse<List<UrlContentDTO>> singleInfoSearchAndSave(InfoSearchRequest request) {
        log.info("singleInfoSearchAndSave,request={}", JSON.toJSONString(request));
        if (request == null || CollectionUtils.isEmpty(request.getUrlList())) {
            log.info("singleInfoSearchAndSave,requestNull,request={}", JSON.toJSONString(request));
            return null;
        }
        try {
            List<UrlContentDTO> urlContentDTOS = infoSearchManagerMeta.singleInfoSearchAndSave(request);
            if (!CollectionUtils.isEmpty(urlContentDTOS)) {
                return HttpResponse.success(urlContentDTOS);
            }
        } catch (Exception e) {
            log.error("singleInfoSearchAndSave_error,request={}", JSON.toJSONString(request), e);
        }
        log.info("singleInfoSearchAndSave_error,request={}", JSON.toJSONString(request));
        return null;
    }

}