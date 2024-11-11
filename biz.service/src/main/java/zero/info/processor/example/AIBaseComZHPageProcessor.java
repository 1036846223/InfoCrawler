package zero.info.processor.example;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public class AIBaseComZHPageProcessor implements PageProcessor {

    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    @Override
    public void process(Page page) {
        try {
            String url = page.getRequest().getUrl();
            Document document = page.getHtml().getDocument();

            // 提取发布日期
            Element publishDateElement = document.selectFirst("div.flex.items-center.flex-wrap.text-sm.text-surface-500 span:last-child");
            String publishDate = publishDateElement != null ? publishDateElement.text() : "";

            // 提取浏览次数
            Element viewsElement = document.selectFirst("div.inline-flex.items-center.text-surface-500 span");
            String views = viewsElement != null ? viewsElement.text() : "";
//            titleElement.select("h1").text()

            // 提取标题信息
            Element contentElement = document.selectFirst("div.px-4.flex.flex-col.mt-8.md\\:mt-16");
            document.select("h1");

            String title = null;
            String text = null;
            if (contentElement != null) {
                title = contentElement.select("h1").text();

                // 提取文本信息
                // 获取所有 <p> 元素
                Elements paragraphs = contentElement.select("p");

                // 构建一个 StringBuilder 来存储文本
                StringBuilder textContent = new StringBuilder();

                // 遍历 <p> 元素，排除最后一个[论文地址]
                for (int i = 0; i < paragraphs.size() - 1; i++) {
                    Element paragraph = paragraphs.get(i);
                    textContent.append(paragraph.text());  // 添加换行符以分隔段落
                }

                text = textContent.toString();
//                text = contentElement.select("p").text();
                //先把论文地址排除掉

            }

            // 创建 Article 对象并添加到列表中
            ArticleDetails details = new ArticleDetails(publishDate, title, text, url, views);

            // 将结果存储到 page 对象中
            page.putField("details", JSON.toJSONString(details));
        } catch (Exception e) {
            log.error("processError,AIBaseComZHPageProcessor", e);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //single download
//        String url2 = "https://www.aibase.com/zh/news";
        String url2 = "https://www.aibase.com/zh/news/13142";
        String url1 = "https://www.aibase.com/zh/news/13084";
//        Spider.create(new AIBaseComZHProcessor()).addUrl(url2).run();
        AIBaseSearchPagePipeline pipeline = new AIBaseSearchPagePipeline();
        Spider.create(new AIBaseComZHPageProcessor()).addUrl(url1).addUrl(url2).addPipeline(pipeline).run();
        List<ArticleDetails> contents = pipeline.getContents();
//        System.out.println(contents);
    }
}
