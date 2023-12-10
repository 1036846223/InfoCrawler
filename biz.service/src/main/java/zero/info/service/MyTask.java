package zero.info.service;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
public class MyTask implements Task {
    private String uuid;

    public MyTask(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getUUID() {
        return uuid;
    }

    @Override
    public Site getSite() {
        return Site.me(); // 这里可以配置 Site 对象，例如设置重试次数、休眠时间等
    }
}
