package zero.info.manager;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import zero.info.dto.UrlContentDTO;
import zero.info.item.Spider;
import zero.info.processor.example.AIBaseComZHProcessor;
import zero.info.request.InfoSearchRequest;
import zero.info.service.AIBaseSearchPipeline;
import zero.post.dto.ArticleDTO;
import zero.post.dto.ArticleOutDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AIInfoManager {

    private static String AI_BASE_NEWS_URL = "https://www.aibase.com/zh/news";

    public static void main(String[] args) {
        InfoSearchManagerMeta infoSearchManagerMeta = new InfoSearchManagerMeta();
        InfoSearchRequest request = new InfoSearchRequest();
        List<String> urlList = new ArrayList<>();
//        String url1 = "https://www.aibase.com/zh";
        String url1 = "https://www.aibase.com/zh/news";
        urlList.add(url1);
        request.setUrlList(urlList);
        List<UrlContentDTO> urlContentDTOS = infoSearchManagerMeta.singleInfoSearch(request);
        System.out.println(urlContentDTOS);
    }

    public List<ArticleDTO> aiBaseSearch() {
        try {
            AIBaseSearchPipeline pipeline = new AIBaseSearchPipeline();
            Spider.create(new AIBaseComZHProcessor()).addUrl(AI_BASE_NEWS_URL).addPipeline(pipeline).run();
            List<ArticleDTO> contents = pipeline.getContents();
//            System.out.println(contents);
            return contents;
        } catch (Exception e) {
            log.error("aiBaseSearch_error", e);
        }
        return new ArrayList<>();
    }

    public List<ArticleOutDTO> aiBaseSearchOutInfo() {
        try {
            log.info("aiBaseSearchOutInfo_req");
            List<ArticleDTO> articleDTOList = aiBaseSearch();
            if (CollectionUtils.isEmpty(articleDTOList)) {
                return new ArrayList<>();
            }
            List<ArticleOutDTO> outDTOList = articleDTOList.stream().filter(Objects::nonNull)
                    .map(this::ofArticleOutDTO).filter(Objects::nonNull).collect(Collectors.toList());
            return outDTOList;
        } catch (Exception e) {
            log.error("aiBaseSearchOutInfo_error", e);
        }
        return new ArrayList<>();
    }

    public ArticleOutDTO ofArticleOutDTO(ArticleDTO articleDTO) {
        try {
            ArticleOutDTO articleOutDTO = new ArticleOutDTO();
            articleOutDTO.setContent(articleDTO.getTitle());
            return articleOutDTO;

        } catch (Exception e) {
            log.error("ofArticleOutDTO_error", e);
        }
        return null;
    }

}
