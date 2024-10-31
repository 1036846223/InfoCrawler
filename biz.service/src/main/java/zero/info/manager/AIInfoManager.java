package zero.info.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zero.info.dto.UrlContentDTO;
import zero.info.item.Spider;
import zero.info.processor.example.AIBaseComZHProcessor;
import zero.info.request.InfoSearchRequest;
import zero.info.service.AIBaseSearchPipeline;
import zero.post.dto.Article;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AIInfoManager {


    public static void main(String[] args) {
        InfoSearchManagerMeta infoSearchManagerMeta= new InfoSearchManagerMeta();
        InfoSearchRequest request = new InfoSearchRequest();
        List<String> urlList = new ArrayList<>();
//        String url1 = "https://www.aibase.com/zh";
        String url1 = "https://www.aibase.com/zh/news";
        urlList.add(url1);
        request.setUrlList(urlList);
        List<UrlContentDTO> urlContentDTOS = infoSearchManagerMeta.singleInfoSearch(request);
        System.out.println(urlContentDTOS);
    }

    public void aiBaseSearch() {
        try {
            String url2 = "https://www.aibase.com/zh/news";
//        Spider.create(new AIBaseComZHProcessor()).addUrl(url2).run();
            AIBaseSearchPipeline pipeline = new AIBaseSearchPipeline();
            Spider.create(new AIBaseComZHProcessor()).addUrl(url2).addPipeline(pipeline).run();
            List<Article> contents = pipeline.getContents();
        } catch (Exception e) {
            log.error("aiBaseSearch error", e);
        }
    }

}
