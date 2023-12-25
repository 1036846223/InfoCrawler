package zero.info.manager;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import zero.info.dto.OriginContent;
import zero.info.dto.UrlContentDTO;
import zero.info.item.Spider;
import zero.info.processor.example.WeChatPublicAccountProcessor;
import zero.info.request.InfoSearchRequest;
import zero.info.response.HttpResponse;
import zero.info.service.InfoSearchSavePipeline;
import zero.info.utils.BeanMapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 异步,提供批量查询及保存服务
 */
@Service
@Slf4j
public class InfoSearchManagerAsy {
    @Resource
    private InfoSearchManagerMeta infoSearchManagerMeta;


    private static final String CLAZZ_TYPE = InfoSearchManagerAsy.class.getSimpleName();


    //    @Deprecated
//    public static void main(String[] args) {
//        InfoSearchManagerAsy infoSearchManagerASY = new InfoSearchManagerAsy();
//        InfoSearchRequest request = new InfoSearchRequest();
//        List<String> urlList = new ArrayList<>();
//        urlList.add("https://mp.weixin.qq.com/s/jw73RkgyfjQc09v_cbEYzQ");
//        request.setUrlList(urlList);
//        搜索并保存为txt文件
//        infoSearchManagerASY.InfoSearchMetaToSave(request);
//        搜索并返回相关信息
//        List<UrlContentDTO> content = infoSearchManagerASY.InfoSearchSaveAndReturn(request);
//        System.out.println(JSON.toJSONString(content));
//    }
//
    public HttpResponse<Boolean> batchInfoSearchAndSave(InfoSearchRequest request) {
        log.info("batchInfoSearchAndSave,request={}", JSON.toJSONString(request));
        if (request == null || CollectionUtils.isEmpty(request.getUrlList())) {
            log.info("batchInfoSearchAndSave,requestNull,request={}", JSON.toJSONString(request));
            return null;
        }
        try {
            Boolean resData = infoSearchManagerMeta.batchInfoSearchAndSave(request);
            if (resData != null) {
                return HttpResponse.success(resData);
            }
        } catch (Exception e) {
            log.error("batchInfoSearchAndSave_error,request={}", JSON.toJSONString(request), e);
        }
        log.info("batchInfoSearchAndSave_error,request={}", JSON.toJSONString(request));
        return null;
    }


    public UrlContentDTO ofUrlContentDTO(OriginContent originContent) {
        if (originContent == null) {
            return null;
        }
        UrlContentDTO urlContentDTO = BeanMapper.copy(originContent, UrlContentDTO.class);
        urlContentDTO.setContent(originContent.getText());
        return urlContentDTO;
    }
}