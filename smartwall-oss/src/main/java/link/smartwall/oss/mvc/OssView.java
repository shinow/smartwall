package link.smartwall.oss.mvc;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.mvc.View;

import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.OSSObject;

import link.smartwall.oss.OssDownloader;

/**
 * OSS视图
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
public class OssView implements View {
    private String contentType = "image/jpeg";
    private String keyValue;
    private String attachFileName;
    /**
     * 是否缩略图
     */
    private boolean thumbnailImage;

    public OssView(String contentType) {
        if (contentType != null) {
            this.contentType = contentType;
        }
    }

    @Override
    public void render(HttpServletRequest req, HttpServletResponse resp, Object value) throws Throwable {
        resp.setContentType(this.contentType);
        if(this.attachFileName!= null){
            resp.setHeader("Content-disposition","attachment;filename=\""+java.net.URLEncoder.encode(this.attachFileName, "UTF-8")+"\"");
        }
        
        OSSObject ossObject = null;
        ServletOutputStream sos = null;

        try {
            sos = resp.getOutputStream();

            if (this.keyValue != null) {
                ossObject = OssDownloader.getObject(this.keyValue, this.thumbnailImage);
            } else {
                ossObject = OssDownloader.getObject(String.valueOf(value), false);
            }

            byte[] buf = new byte[16 * 1024];
            int bRead;
            while ((bRead = ossObject.getObjectContent().read(buf)) != -1) {
                sos.write(buf, 0, bRead);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sos.flush();
            IOUtils.safeClose(sos);
            IOUtils.safeClose(ossObject.getObjectContent());
        }
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public boolean isThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(boolean thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getAttachFileName() {
        return attachFileName;
    }

    public void setAttachFileName(String attachFileName) {
        this.attachFileName = attachFileName;
    }
}
