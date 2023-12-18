package zero.info.service;


import zero.info.dto.OriginContent;
import zero.info.item.ResultItems;
import zero.info.item.Task;
import zero.info.pipeline.Pipeline;

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
