package zero.info.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import zero.info.item.ResultItems;
import zero.info.item.Task;
import zero.info.pipeline.Pipeline;
import zero.post.dto.ArticleDetails;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class AIBaseSearchPagePipeline implements Pipeline {

    private List<ArticleDetails> contents = new CopyOnWriteArrayList<>();

    @Override
    public void process(ResultItems resultItems, Task task) {
        String articlesStr = resultItems.get("details").toString();
        ArticleDetails details = JSON.parseObject(articlesStr, ArticleDetails.class);
        contents.add(details);
    }

    public List<ArticleDetails> getContents() {
        return contents;
    }
}