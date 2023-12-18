package zero.info.service;

import org.springframework.stereotype.Service;
import zero.info.item.ResultItems;
import zero.info.item.Task;
import zero.info.pipeline.Pipeline;

import java.util.concurrent.*;

@Deprecated
@Service
public class ZhihuPipelineV1 implements Pipeline {

    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ConcurrentMap<Task, Future<ResultItems>> taskResults = new ConcurrentHashMap<>();

    @Override
    public void process(ResultItems resultItems, Task task) {
        Callable<ResultItems> callable = () -> {
            // 这里可以进行数据处理
            return resultItems;
        };
        Future<ResultItems> futureResult = executorService.submit(callable);
        taskResults.put(task, futureResult);
    }

    public ResultItems getProcessedData(Task task) throws ExecutionException, InterruptedException, TimeoutException {
        Future<ResultItems> futureResult = taskResults.remove(task); // 移除操作也应该同步
        if (futureResult != null) {
            return futureResult.get(10, TimeUnit.SECONDS);
        }
        return null;
    }
}