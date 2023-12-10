package zero.info.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;
import java.util.function.Supplier;


@Slf4j
@Service
public class ConcurrentHelper {

    private static Executor helperThreadPool = new ThreadPoolExecutor(
            300, // 核心线程池大小
            500, // 最大线程池大小
            5L, // 线程保持活动时间
            TimeUnit.MINUTES, // 时间单位
            new SynchronousQueue<Runnable>(), // 任务队列
            new CustomThreadFactory("helperThreadPool-") // 使用自定义线程工厂
    );


    /**
     * @param mapSupplier
     * @param <T>
     * @return
     */
    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> mapSupplier) {
        return CompletableFuture.supplyAsync(mapSupplier, helperThreadPool);
    }

    public static <T> CompletableFuture<T> supplyAsync(Supplier<T> mapSupplier, Executor executor) {
        return CompletableFuture.supplyAsync(mapSupplier, executor);
    }

    /**
     * @param runnable
     */
    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, helperThreadPool);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor) {
        return CompletableFuture.runAsync(runnable, executor);
    }
}
