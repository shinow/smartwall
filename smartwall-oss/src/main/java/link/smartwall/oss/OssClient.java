package link.smartwall.oss;

/**
 * OSS客户端
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since SFA 5.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2017-01-08 lexloo
 *        </pre>
 */
public class OssClient {
    /**
     * 关闭 客户端
     */
    public static void shoutdown() {
        OssUploader.shoutdown();
        OssDownloader.shoutdown();
    }
}
