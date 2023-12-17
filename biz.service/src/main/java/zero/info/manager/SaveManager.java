package zero.info.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

@Slf4j
@Service
public class SaveManager {
    // 创建一个DateTimeFormatter对象，定义日期格式
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HH-mm-ss.SSS"); // 日期和时间格式化模式

    private static Executor helperThreadPool = new ThreadPoolExecutor(
            5, // 核心线程池大小
            50, // 最大线程池大小
            5L, // 线程保持活动时间
            TimeUnit.MINUTES, // 时间单位
            new SynchronousQueue<Runnable>() // 任务队列
    );


    public static void saveText(String content) {
        try {
            String filePath = String.format("%s%s", "wc", LocalDateTime.now().format(formatter));
            CompletableFuture.runAsync(() ->
                    overWriteToFile(content, filePath), helperThreadPool);
        } catch (Exception e) {
            log.error("saveTextError", e);
            System.out.println(e);
        }
    }

    public static void overWriteToFile(String content, String filePath) {
        try {
            FileWriter writer = new FileWriter(filePath, false);
            writer.write(content);
            writer.close();
//            System.out.println("save_finish:" + filePath + "------" + content);
//            System.out.println("save_finish:" + filePath + "------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void increaseWriteToFile(String content, String filePath) {
        File file = new File(filePath);
        String fileExtension = "";
        String fileNameWithoutExtension = filePath;

        // 如果文件存在，则分离文件扩展名和文件名
        int dotIndex = filePath.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = filePath.substring(dotIndex);
            fileNameWithoutExtension = filePath.substring(0, dotIndex);
        }

        // 如果文件已存在，则在文件名后添加递增数字
        int counter = 1;
        while (file.exists()) {
            file = new File(fileNameWithoutExtension + counter + fileExtension);
            counter++;
        }

        // 写入新文件
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(content);
            // System.out.println("save_finish:" + file.getPath() + "------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
