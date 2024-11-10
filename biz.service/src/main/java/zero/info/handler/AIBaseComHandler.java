package zero.info.handler;

import lombok.extern.slf4j.Slf4j;
import zero.info.enu.WebSiteParseTypeEnum;
import zero.info.item.Spider;
import zero.info.processor.example.AIBaseComZHProcessor;
import zero.info.service.AIBaseSearchPipeline;
import zero.post.dto.ArticleDTO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AIBaseComHandler implements WebSiteParseHandler {

    @Override
    public List<ArticleDTO> defaultSearch() {
        try {
            AIBaseSearchPipeline pipeline = new AIBaseSearchPipeline();
            Spider.create(new AIBaseComZHProcessor()).addUrl(WebSiteParseTypeEnum.AI_BASE_COM.getUrl()).addPipeline(pipeline).run();
            List<ArticleDTO> contents = pipeline.getContents();
            return contents;
        } catch (Exception e) {
            log.error("aiBaseSearch_error", e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<ArticleDTO> multSearch(List<String> urlList) {
        return null;
    }

    @Override
    public boolean support(WebSiteParseTypeEnum webSiteParseTypeEnum) {
        return WebSiteParseTypeEnum.AI_BASE_COM.equals(webSiteParseTypeEnum);
    }
}
