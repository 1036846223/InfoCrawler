package zero.info.handler;


import zero.info.enu.WebSiteParseTypeEnum;
import zero.post.dto.ArticleDTO;

import java.util.List;

public interface WebSiteParseHandler {

    List<ArticleDTO> defaultSearch();

    List<ArticleDTO> multSearch(List<String> urlList);

    boolean support(WebSiteParseTypeEnum webSiteParseTypeEnum);
}
