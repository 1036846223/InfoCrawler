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
import zero.info.service.InfoSearchPipeline;
import zero.info.service.InfoSearchSavePipeline;
import zero.info.utils.BeanMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 查询及保存服务等基础服务
 * chaser
 */
@Service
@Slf4j
public class InfoSearchManagerMeta {


    private static final String CLAZZ_TYPE = InfoSearchManagerMeta.class.getSimpleName();


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

    /**
     * 批量查询并保存不返回信息
     */
    public Boolean batchInfoSearchAndSave(InfoSearchRequest request) {
        log.info("batchInfoSearchAndSave,request={}", JSON.toJSONString(request));
        if (request == null || CollectionUtils.isEmpty(request.getUrlList())) {
            log.info("batchInfoSearchAndSave,requestNull,request={}", JSON.toJSONString(request));
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
            log.info("batchInfoSearchAndSave_res,request={},res=true", JSON.toJSONString(request));
            return true;
        } catch (Exception e) {
            log.error("batchInfoSearchAndSave_error,request={}", JSON.toJSONString(request), e);
        } finally {
            if (spider != null) {
                spider.stop(); // 假设spider提供了stop方法来释放资源
            }
        }
        log.info("InfoSearchMetaToSave_error,request={}", JSON.toJSONString(request));
        return null;
    }

    /**
     * 单个查询并保存 返回信息
     */
    public List<UrlContentDTO> singleInfoSearchAndSave(InfoSearchRequest request) {
        log.info("singleInfoSearchAndSave,request={}", JSON.toJSONString(request));
        if (request == null || CollectionUtils.isEmpty(request.getUrlList())) {
            log.info("singleInfoSearchAndSave,requestNull,request={}", JSON.toJSONString(request));
            return null;
        }
        try {
            // 配置 URL 列表
            InfoSearchSavePipeline pipeline = new InfoSearchSavePipeline();
            Spider.create(new WeChatPublicAccountProcessor()).addUrl(request.getUrlList().get(0)).addPipeline(pipeline).run();
            List<OriginContent> contents = pipeline.getContents();
            if (!CollectionUtils.isEmpty(contents)) {
                List<UrlContentDTO> contentDTOList = contents.stream().map(this::ofUrlContentDTO).filter(Objects::nonNull).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(contentDTOList)) {
                    log.info("singleInfoSearchAndSave_res,request={},res={}", JSON.toJSONString(request), JSON.toJSONString(contentDTOList));
                    return contentDTOList;
                }
            }
        } catch (Exception e) {
            log.error("singleInfoSearchAndSave_error,request={}", JSON.toJSONString(request), e);
        }
        log.info("singleInfoSearchAndSave_error,request={}", JSON.toJSONString(request));
        return null;
    }

    /**
     * 单个查询不保存 返回信息
     */
    public List<UrlContentDTO> singleInfoSearch(InfoSearchRequest request) {
        log.info("singleInfoSearch,request={}", JSON.toJSONString(request));
        if (request == null || CollectionUtils.isEmpty(request.getUrlList())) {
            log.info("singleInfoSearch,requestNull,request={}", JSON.toJSONString(request));
            return null;
        }
        try {
            // 配置 URL 列表
            InfoSearchPipeline pipeline = new InfoSearchPipeline();
            Spider.create(new WeChatPublicAccountProcessor()).addUrl(request.getUrlList().get(0)).addPipeline(pipeline).run();
            List<OriginContent> contents = pipeline.getContents();
            if (!CollectionUtils.isEmpty(contents)) {
                List<UrlContentDTO> contentDTOList = contents.stream().map(this::ofUrlContentDTO).filter(Objects::nonNull).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(contentDTOList)) {
                    log.info("singleInfoSearch_res,request={},res={}", JSON.toJSONString(request), JSON.toJSONString(contentDTOList));
                    return contentDTOList;
                }
            }
        } catch (Exception e) {
            log.error("singleInfoSearch_error,request={}", JSON.toJSONString(request), e);
        }
        log.info("singleInfoSearch_error,request={}", JSON.toJSONString(request));
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
