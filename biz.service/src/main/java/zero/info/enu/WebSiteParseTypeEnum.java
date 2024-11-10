package zero.info.enu;

import zero.info.processor.example.AIBaseComZHProcessor;
import zero.info.service.AIBaseSearchPipeline;

public enum WebSiteParseTypeEnum {
    AI_BASE_COM("https://www.aibase.com/zh/news", AIBaseSearchPipeline.class, AIBaseComZHProcessor.class),
    ;
    private String url;
    private Class pipeline;
    private Class pageProcessor;

    WebSiteParseTypeEnum(String url, Class pipeline, Class pageProcessor) {
        this.url = url;
        this.pipeline = pipeline;
        this.pageProcessor = pageProcessor;
    }

    public String getUrl() {
        return url;
    }

    public Class getPipeline() {
        return pipeline;
    }

    public Class getPageProcessor() {
        return pageProcessor;
    }
}
