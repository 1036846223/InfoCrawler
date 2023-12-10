package zero.info.service;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import web.dto.OriginContent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ZhihuPipeline implements Pipeline {

    private List<OriginContent> contents = new CopyOnWriteArrayList<>();


    @Override
    public void process(ResultItems resultItems, Task task) {
        OriginContent content = new OriginContent();
        content.setTitle(resultItems.get("title").toString());
        content.setQuestion(resultItems.get("question").toString());
        content.setAnswer(resultItems.get("answer").toString());
        content.setText(resultItems.get("text").toString());
        contents.add(content);
    }

    public List<OriginContent> getContents() {
        return contents;
    }
}
