package zero.info.manager;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 异步,提供批量查询及保存服务
 * chaser
 */
@Service
@Slf4j
public class InfoSearchManagerAsy {


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


    public Boolean InfoSearchMetaToSave(InfoSearchRequest request) {
        log.info("InfoSearchMetaToSave,request={}", JSON.toJSONString(request));
        if (request == null || CollectionUtils.isEmpty(request.getUrlList())) {
            log.info("InfoSearchMetaToSave,requestNull,request={}", JSON.toJSONString(request));
            return null;
        }
        Spider spider = null;
        try {
            // 配置 URL 列表
            List<String> urlList = request.getUrlList();
            InfoSearchSavePipeline pipeline = new InfoSearchSavePipeline();
            spider = Spider.create(new WeChatPublicAccountProcessor()).addPipeline(pipeline);
            for (String url : urlList) {
                spider.addUrl(url);
            }
            spider.run();
            log.info("InfoSearchMetaToSave_res,request={},res=true", JSON.toJSONString(request));
            return true;
        } catch (Exception e) {
            log.error("InfoSearchMetaToSave_error,request={}", JSON.toJSONString(request), e);
        } finally {
            if (spider != null) {
                spider.stop(); // 假设spider提供了stop方法来释放资源
            }
        }
        log.info("InfoSearchMetaToSave_error,request={}", JSON.toJSONString(request));
        return null;
    }

    public List<UrlContentDTO> InfoSearchSaveAndReturn(InfoSearchRequest request) {
        log.info("InfoSearchSaveAndReturn,request={}", JSON.toJSONString(request));
        if (request == null || CollectionUtils.isEmpty(request.getUrlList())) {
            log.info("InfoSearchSaveAndReturn,requestNull,request={}", JSON.toJSONString(request));
            return null;
        }
        Spider spider = null;
        try {
            // 配置 URL 列表
            List<String> urlList = request.getUrlList();
            InfoSearchSavePipeline pipeline = new InfoSearchSavePipeline();
            spider = Spider.create(new WeChatPublicAccountProcessor()).addPipeline(pipeline);
            for (String url : urlList) {
                spider.addUrl(url);
            }
            spider.run();
            List<OriginContent> contents = pipeline.getContents();
            if (!CollectionUtils.isEmpty(contents)) {
                List<UrlContentDTO> contentDTOList = contents.stream().map(this::ofUrlContentDTO).filter(Objects::nonNull).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(contentDTOList)) {
                    log.info("InfoSearchSaveAndReturn_res,request={},res={}", JSON.toJSONString(request), JSON.toJSONString(contentDTOList));
                    return contentDTOList;
                }
            }
        } catch (Exception e) {
            log.error("InfoSearchSaveAndReturn_error,request={}", JSON.toJSONString(request), e);
        } finally {
            if (spider != null) {
                spider.stop(); // 假设spider提供了stop方法来释放资源
            }
        }
        log.info("InfoSearchSaveAndReturn_error,request={}", JSON.toJSONString(request));
        return null;
    }

    public HttpResponse<List<UrlContentDTO>> InfoSearchRes(InfoSearchRequest request) {
        log.info("InfoSearchRes,request={}", JSON.toJSONString(request));
        if (request == null || CollectionUtils.isEmpty(request.getUrlList())) {
            log.info("InfoSearchRes,requestNull,request={}", JSON.toJSONString(request));
            return HttpResponse.paramError();
        }
        try {
            List<UrlContentDTO> res = InfoSearchSaveAndReturn(request);
            if (CollectionUtils.isEmpty(res)) {
                return HttpResponse.success(res);
            }
            return HttpResponse.success();
        } catch (Exception e) {
            log.error("InfoSearchRes_error,request={}", JSON.toJSONString(request), e);
            return HttpResponse.error(e.getMessage());
        }
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
