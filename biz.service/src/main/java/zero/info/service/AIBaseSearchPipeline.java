package zero.info.service;

import com.alibaba.fastjson.JSON;
import zero.info.dto.OriginContent;
import zero.info.item.ResultItems;
import zero.info.item.Task;
import zero.info.manager.SaveManager;
import zero.info.pipeline.Pipeline;
import zero.post.dto.Article;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class AIBaseSearchPipeline implements Pipeline {

    private List<Article> contents = new CopyOnWriteArrayList<>();

    @Override
    public void process(ResultItems resultItems, Task task) {
        String articlesStr = resultItems.get("articles").toString();
        contents = JSON.parseObject(articlesStr, List.class);
    }

    public List<Article> getContents() {
        return contents;
    }
}