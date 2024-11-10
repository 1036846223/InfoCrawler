package zero.info.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zero.info.enu.WebSiteParseTypeEnum;
import zero.post.dto.ArticleDTO;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WebSiteParseChoose {
    private final List<WebSiteParseHandler> webSiteParseHandlerList = new ArrayList<>();

    public WebSiteParseChoose() {
        webSiteParseHandlerList.add(new AIBaseComHandler());
    }

    public List<ArticleDTO> defaultSearch(WebSiteParseTypeEnum webSiteParseTypeEnum) {
        for (WebSiteParseHandler webSiteParseHandler : webSiteParseHandlerList) {
            if (webSiteParseHandler.support(webSiteParseTypeEnum)) {
                return webSiteParseHandler.defaultSearch();
            }
        }
        return null;
    }

    public List<ArticleDTO> multSearch(List<String> urlList, WebSiteParseTypeEnum webSiteParseTypeEnum) {
        for (WebSiteParseHandler webSiteParseHandler : webSiteParseHandlerList) {
            if (webSiteParseHandler.support(webSiteParseTypeEnum)) {
                return webSiteParseHandler.multSearch(urlList);
            }
        }
        return null;
    }
}
