package zero.info.manager;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import zero.info.dto.UrlContentDTO;
import zero.info.request.InfoSearchRequest;
import zero.info.response.HttpResponse;
import zero.info.service.ZhihuCrawlerServiceV1;
import zero.info.service.ZhihuPipelineV1;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Deprecated
@Service
@Slf4j
public class InfoSearchManagerV1 {

    @Resource
    private ZhihuCrawlerServiceV1 zhihuCrawlerServiceV1;
    @Resource
    private ZhihuPipelineV1 zhihuPipelineV1;

    private static final String CLAZZ_TYPE = InfoSearchManagerV1.class.getSimpleName();


    public List<UrlContentDTO> InfoSearchMeta(InfoSearchRequest request) {
        log.info("InfoSearch,request={}", JSON.toJSONString(request));
        try {
            // 配置 URL 列表
            List<String> urlList = request.getUrlList();

            // 创建 Task 实例
            String uuid = UUID.randomUUID().toString(); // 生成唯一标识符
            Task task = new MyTask(uuid);

            // 使用 Task 实例启动爬虫任务
            zhihuCrawlerServiceV1.crawl(urlList, zhihuPipelineV1);

            // 获取处理结果
            ResultItems processedData = zhihuPipelineV1.getProcessedData(task);

            // 处理或使用爬取的数据
            if (processedData != null) {
                // 例如，打印获取到的结果
                System.out.println(processedData.getAll());
            }

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
