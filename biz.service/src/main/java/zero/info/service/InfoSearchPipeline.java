package zero.info.service;

import zero.info.dto.OriginContent;
import zero.info.item.ResultItems;
import zero.info.item.Task;
import zero.info.manager.SaveManager;
import zero.info.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class InfoSearchPipeline implements Pipeline {

    private List<OriginContent> contents = new CopyOnWriteArrayList<>();
    @Resource
    private SaveManager saveManager;
    @Override
    public void process(ResultItems resultItems, Task task) {
        OriginContent content = new OriginContent();
        content.setText(resultItems.get("text").toString());
        contents.add(content);
    }

    public List<OriginContent> getContents() {
        return contents;
    }
}