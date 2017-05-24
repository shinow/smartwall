package link.smartwall.oss;

import java.io.InputStream;

import com.aliyun.oss.OSSClient;

/**
 * OSS文件上传
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
public class OssUploader {
    /* 登录阿里云查看相关信息 */
    public static final String END_POINT = "oss-cn-hangzhou.aliyuncs.com";// "lexloo-th.oss-cn-hangzhou-internal.aliyuncs.com";
    public static final String ACCESS_KEY_ID = "LTAIwnXpCqX9uwFA";
    public static final String ACCESS_KEY_SECRET = "fStNHpNFToE4SoKRouVTNv9ftwgsp3";
    public static final String BUCKET = "lexloo-th";

    private static OSSClient client;

    private static void init() {
        client = new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    static {
        init();
    }

    /**
     * 客户端
     */
    static void shoutdown() {
        client.shutdown();
    }

    /**
     * 上传图片
     * 
     * @param tenantId 企业Id
     * @param fileName 文件夹名
     * @param inputStream 数据流
     * @return 成功与否
     */
    public static boolean uploadImage(int tenantId, String fileName, InputStream inputStream) {
        try {
            client.putObject(OssUploader.BUCKET, tenantId + "/" +  fileName, inputStream);

            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
