package zero.info.service;

import zero.info.dto.OriginContent;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InfoSearchSavePipeline implements Pipeline {

    private List<OriginContent> contents = new CopyOnWriteArrayList<>();
    @Resource
    private SaveManager saveManager;

    @Override
    public void process(ResultItems resultItems, Task task) {
        OriginContent content = new OriginContent();
        String allText=resultItems.get("text").toString();
        content.setText(resultItems.get("text").toString());
        contents.add(content);
        //保存
        saveManager.saveText(allText);
    }

    public List<OriginContent> getContents() {
        return contents;
    }
}
