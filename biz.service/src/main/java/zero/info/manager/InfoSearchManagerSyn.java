package zero.info.manager;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
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
 * chaser
 */
@Service
@Slf4j
public class InfoSearchManagerSyn {

    @Resource
    private ZhihuCrawlerServiceV1 zhihuCrawlerServiceV1;
    @Resource
    private ZhihuPipelineV1 zhihuPipelineV1;

    private static final String CLAZZ_TYPE = InfoSearchManagerSyn.class.getSimpleName();


    public static void main(String[] args) {
        InfoSearchManagerSyn infoSearchManagerASY = new InfoSearchManagerSyn();
        InfoSearchRequest request= new InfoSearchRequest();
        List<String> urlList= new ArrayList<>();
        String url1="https://mp.weixin.qq.com/s/Jz6RzHwg1nOTbhqLFSiDrQ";
        urlList.add(url1);
        request.setUrlList(urlList);
        infoSearchManagerASY.InfoSearchMetaV2(request);
    }



    public List<UrlContentDTO> InfoSearchMetaV2(InfoSearchRequest request) {
        log.info("InfoSearch,request={}", JSON.toJSONString(request));
        if (request == null || CollectionUtils.isEmpty(request.getUrlList())) {
            log.info("InfoSearch,requestNull,request={}", JSON.toJSONString(request));
            return null;
        }
        try {
            // 配置 URL 列表
            List<String> urlList = request.getUrlList();
            Spider spider = Spider.create(new WeChatPublicAccountProcessor()).thread(2);
            String urlTemplate = urlList.get(0);
            ResultItems resultItems = spider.<ResultItems>get(String.format(urlTemplate));

            log.info("InfoSearch_res,request={},res=null", JSON.toJSONString(request));
            spider.stop();
            return null;
        } catch (Exception e) {
            log.error("InfoSearch_error,request={}", JSON.toJSONString(request), e);
        }
        log.info("InfoSearch_error,request={}", JSON.toJSONString(request));
        return null;
    }

    public List<UrlContentDTO> InfoSearchMeta(InfoSearchRequest request) {
        log.info("InfoSearch,request={}", JSON.toJSONString(request));
        if (request == null || CollectionUtils.isEmpty(request.getUrlList())) {
            log.info("InfoSearch,requestNull,request={}", JSON.toJSONString(request));
            return null;
        }
        try {
            // 配置 URL 列表
            List<String> urlList = request.getUrlList();
            ZhihuPipeline pipeline = new ZhihuPipeline();
            Spider spider = Spider.create(new WeChatPublicAccountProcessor()).addPipeline(pipeline);
            for (String url : urlList) {
                spider.addUrl(url);
            }
            spider.run();
            List<OriginContent> zhihuContents = pipeline.getContents();

            log.info("InfoSearch_res,request={},res=null", JSON.toJSONString(request));
            return null;
        } catch (Exception e) {
            log.error("InfoSearch_error,request={}", JSON.toJSONString(request), e);
        }
        log.info("InfoSearch_error,request={}", JSON.toJSONString(request));
        return null;
    }

    public HttpResponse<List<UrlContentDTO>> batchInfoSearch(InfoSearchRequest request) {
        log.info("InfoSearch,request={}", JSON.toJSONString(request));
        try {
            log.info("InfoSearch_res,request={},res=null", JSON.toJSONString(request));
            return HttpResponse.success(null);
        } catch (Exception e) {
            log.error("InfoSearch_error,request={}", JSON.toJSONString(request), e);
        }
        log.info("InfoSearch_error,request={}", JSON.toJSONString(request));
        return HttpResponse.systemError();
    }
}
