package zero.info.service;

public class MyTask {
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
