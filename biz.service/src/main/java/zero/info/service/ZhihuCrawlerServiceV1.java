package zero.info.service;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.ZhihuPageProcessor;

import java.util.List;
import java.util.concurrent.*;

@Deprecated
@Service
public class ZhihuCrawlerServiceV1 {

    private final ExecutorService executorService = new ThreadPoolExecutor(
            5, // 核心线程池大小
            30, // 最大线程池大小
            0L, TimeUnit.MILLISECONDS, // 非核心线程空闲存活时间
            new LinkedBlockingQueue<Runnable>(100), // 任务队列容量
            Executors.defaultThreadFactory(),
            new ThreadPoolExecutor.CallerRunsPolicy() // 饱和策略
    );


    public void crawl(List<String> urls, Pipeline pipeline) {
        if (CollectionUtils.isEmpty(urls)) {
            return;
        }
        // 使用线程安全的方式复制 urls 列表
        List<String> safeUrls = new CopyOnWriteArrayList<>(urls);
        executorService.submit(() -> {
            PageProcessor processor = createNewProcessorInstance();
            Spider spider = Spider.create(processor).addPipeline(pipeline);
            // 使用线程安全的集合遍历
            for (String url : safeUrls) {
                spider.addUrl(url);
            }
            spider.thread(1).run();
        });
    }


    private PageProcessor createNewProcessorInstance() {
        // 实现创建新的PageProcessor实例的逻辑
        return new ZhihuPageProcessor();
    }
}
