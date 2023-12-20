package zero.info.processor.example;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import zero.info.item.Page;
import zero.info.item.ResultItems;
import zero.info.item.Site;
import zero.info.item.Spider;
import zero.info.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;


public class WeChatPublicAccountProcessor implements PageProcessor {

    private Site site = Site.me()//.setHttpProxy(new HttpHost("127.0.0.1",8888))
            .setRetryTimes(3).setSleepTime(1000).setUseGzip(true);

    @Override
    public void process(Page page) {

        Document doc = page.getHtml().getDocument();;
        Elements divElements = doc.select("div");
        StringBuilder divText = new StringBuilder();
        for (Element div : divElements) {
            if (StringUtils.isNotEmpty(div.text())) {
                //暂无比较好的数据清洗办法,就只取第一个
                divText.append(div.text());
                break;
            }
        }
        page.putField("text", divText.toString());

//        System.out.println(divText.toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        //single download
        String urlTemplate = "https://mp.weixin.qq.com/s/Jz6RzHwg1nOTbhqLFSiDrQ";
        String url2="https://mp.weixin.qq.com/s/uR5dYC-d4FPClEI-HqxsjA";
        Spider.create(new WeChatPublicAccountProcessor()).addUrl(url2).run();

    }
}
