package zero.info.processor.example;

import com.alibaba.fastjson.JSON;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import zero.info.item.Page;
import zero.info.item.Site;
import zero.info.item.Spider;
import zero.info.processor.PageProcessor;
import zero.info.service.AIBaseSearchPagePipeline;
import zero.info.service.AIBaseSearchPipeline;
import zero.post.dto.ArticleDTO;
import zero.post.dto.ArticleDetails;

import java.util.ArrayList;
import java.util.List;


public class AIBaseComZHProcessor implements PageProcessor {

    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);
    private static String baseUrl = "https://www.aibase.com";

    @Override
    public void process(Page page) {
        Document document = page.getHtml().getDocument();

        // 选择所有包含时间、标题和介绍信息的父元素
        Elements articleElements = document.select("a.flex.group.justify-between");

        List<ArticleDTO> articleDTODTOS = new ArrayList<>();

        // 遍历每个父元素，提取时间、标题和介绍信息
        for (Element articleElement : articleElements) {

            // 提取链接信息
            String link = articleElement.attr("href");
            String url = baseUrl + link;

//            // 提取时间信息
            Element timeElement = articleElement.selectFirst("div.text-sm.text-gray-400.flex.items-center.space-x-1");
            String time = timeElement != null ? timeElement.text() : "";

            // 提取标题信息
            Element titleElement = articleElement.selectFirst("div.w-full h3.line-clamp-2.md\\:text-xl.text-lg.text-surface-800");
            String title = titleElement != null ? titleElement.text() : "";

            // 提取介绍信息，确保选择器路径准确
            Element introElement = articleElement.selectFirst("div.py-2.hidden.md\\:block");
            String introduction = introElement != null ? introElement.text() : "";

            // 创建 Article 对象并添加到列表中
            ArticleDTO articleDTO = new ArticleDTO(time, title, introduction, url);
            articleDTODTOS.add(articleDTO);
        }

        // 将结果存储到 page 对象中
        page.putField("articles", JSON.toJSONString(articleDTODTOS));

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //single download
        String url2 = "https://www.aibase.com/zh/news/13142";
//        String url1 = "https://www.aibase.com/zh/news/13084";
//        Spider.create(new AIBaseComZHProcessor()).addUrl(url2).run();
        AIBaseSearchPipeline pipeline = new AIBaseSearchPipeline();
        Spider.create(new AIBaseComZHProcessor()).addUrl(url2).addPipeline(pipeline).run();
        List<ArticleDTO> contents = pipeline.getContents();
        System.out.println(contents);
    }
}
