package zero.info.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import zero.info.item.ResultItems;
import zero.info.item.Task;
import zero.info.pipeline.Pipeline;
import zero.post.dto.ArticleDTO;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class AIBaseSearchPipeline implements Pipeline {

    private List<ArticleDTO> contents = new CopyOnWriteArrayList<>();

    @Override
    public void process(ResultItems resultItems, Task task) {
        String articlesStr = resultItems.get("articles").toString();
        List<JSONObject> contentsJsonObject = JSON.parseObject(articlesStr, List.class);
        for (JSONObject content : contentsJsonObject) {
            String time = content.getString("time");
            String title = content.getString("title");
            String introduction = content.getString("introduction");
            String url = content.getString("url");
            contents.add(new ArticleDTO(time, title, introduction, url));
        }
    }

    public List<ArticleDTO> getContents() {
        return contents;
    }
}