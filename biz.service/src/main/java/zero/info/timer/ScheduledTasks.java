package zero.info.timer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zero.info.manager.AIContentTimeManager;
import zero.info.manager.AIInfoManager;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class ScheduledTasks {
    @Resource
    private AIInfoManager aiInfoManager;
    @Resource
    private AIContentTimeManager aiContentTimeManager;

    //每天9点03分运行一次
//    @Scheduled(cron = "0 3 9 * * ?")
    public void performTask() {
        try {
            Long startTime = new Date().getTime();
            log.info("任务正在运行,startTime={}", startTime);
            aiInfoManager.aiBaseSearch();
            Long endTime = new Date().getTime();
            log.info("任务运行结束,endTime={},processTime={}", endTime, endTime - startTime);
        } catch (Exception e) {
            log.error("performTask_error", e);
        }
    }

    //每小时运行一次 todo
    //每小时的第5分钟第6秒执行一次任务
    @Scheduled(cron = "6 5 * * * ?")
    public void dataCollectionTask() {
        try {
            Long startTime = new Date().getTime();
            log.info("任务正在运行,dataCollectionTask,startTime={}", startTime);
            aiContentTimeManager.dataCollection();
            Long endTime = new Date().getTime();
            log.info("任务运行结束,dataCollectionTask,endTime={},processTime={}", endTime, endTime - startTime);
        } catch (Exception e) {
            log.error("dataCollectionTask_error", e);
        }
    }
}
