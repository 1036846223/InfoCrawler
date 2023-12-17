package zero.info.downloader;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class IOUtilsReplacement {
    public static byte[] toByteArray(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        byte[] bytes = new byte[1024];
        int read;

        while ((read = inputStream.read(bytes)) != -1) {
            // 确保数组有足够的空间来容纳读取的字节
            if (read == bytes.length) {
                byte[] newBytes = new byte[bytes.length * 2];
                System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
                bytes = newBytes;
            }

            // 更新字节数组的有效长度
            bytes = Arrays.copyOf(bytes, bytes.length + read);
        }

        return bytes;
    }
    public static byte[] toByteArrayByContent(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[1024];
        int read;

        while ((read = inputStream.read(bytes)) != -1) {
            // 确保数组有足够的空间来容纳读取的字节
            if (read == bytes.length) {
                byte[] newBytes = new byte[bytes.length * 2];
                System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
                bytes = newBytes;
            }

            // 更新字节数组的有效长度
            bytes = Arrays.copyOf(bytes, bytes.length + read);
        }

        return bytes;
    }
}