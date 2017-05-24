package link.smartwall.oss;

import java.io.File;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;

/**
 * OSS文件下载
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
public class OssDownloader {
    /* 登录阿里云查看相关信息 */
    public static final String END_POINT = "oss-cn-hangzhou.aliyuncs.com";// "lexloo-th.oss-cn-hangzhou-internal.aliyuncs.com";
    public static final String ACCESS_KEY_ID = "LTAIwnXpCqX9uwFA";
    public static final String ACCESS_KEY_SECRET = "fStNHpNFToE4SoKRouVTNv9ftwgsp3";
    public static final String BUCKET = "lexloo-th";

    private static OSSClient client;

    private static void init() {
        ClientConfiguration conf = new ClientConfiguration();
        conf.setSocketTimeout(10000);
        client = new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET, conf);
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
     * @param tenantId 企业id
     * @param fileName 文件名
     * @param key 数据键
     * @return 是否成功
     */
    public static boolean downloadImage(int tenantId, String fileName, String key) {
        try {
            client.getObject(new GetObjectRequest(OssDownloader.BUCKET, tenantId + "/" + key), new File(fileName));

            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 获取OSS对象
     * 
     * @param key 对象键值
     * @param thumbnail 缩略图
     * @return 对象
     */
    public static OSSObject getObject(String key, boolean thumbnail) {
        if (thumbnail) {
            String style = "image/resize,m_fixed,h_84,w_84";
            GetObjectRequest request = new GetObjectRequest(OssDownloader.BUCKET, key);
            request.setProcess(style);
            return client.getObject(request);
        } else {
            return client.getObject(OssDownloader.BUCKET, key);
        }
    }
}
