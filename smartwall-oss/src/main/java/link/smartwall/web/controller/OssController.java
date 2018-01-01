package link.smartwall.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import link.smartwall.oss.OssUploader;
import link.smartwall.oss.mvc.OssView;

/**
 * OSS MVC Controller
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
@IocBean
public class OssController {
    /**
     * ContentType Map
     */
    private static Map<String, String> contentTypeMap = new HashMap<String, String>();

    static {
        contentTypeMap.put("jpg", "image/jpeg");
        contentTypeMap.put("jpeg", "image/jpeg");
        contentTypeMap.put("tif", "image/tiff");
        contentTypeMap.put("avi", "video/avi");
        contentTypeMap.put("bmp", "application/x-bmp ");
        contentTypeMap.put("doc", "application/msword");
        contentTypeMap.put("gif", "image/gif");
        contentTypeMap.put("htm", "text/html");
        contentTypeMap.put("html", "text/html");
        contentTypeMap.put("pdf", "application/pdf");
        contentTypeMap.put("mp4", "video/mpeg4");
        contentTypeMap.put("mp3", "audio/mp3");
        contentTypeMap.put("mpeg", "video/mpg");
        contentTypeMap.put("ppt", "application/vnd.ms-powerpoint");
        contentTypeMap.put("png", "image/png");
        contentTypeMap.put("txt", "text/plain");
        contentTypeMap.put("xls", "application/vnd.ms-excel");
        contentTypeMap.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        contentTypeMap.put("xslx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    /**
     * 显示图片
     * 
     * @param key 图片键值
     */
    @At("/oss/mobi/image")
    @Ok("oss:image/jpeg")
    public String showOssMobiImage(@Param("key") String key) {
        return key;
    }

    /**
     * 显示图片
     * 
     * @param key 图片键值
     * @param thumbnail 是否缩略图
     */
    @At("/oss/web/image")
    @Ok("oss:image/jpeg")
    public View showOssWebImage(@Param("key") String key, @Param("thumbnail") boolean thumbnail) {
        OssView view = new OssView("image/jpeg");
        view.setKeyValue(key);

        if (thumbnail) {
            view.setThumbnailImage(thumbnail);
        }

        return view;
    }

    /**
     * 显示各种资源
     * 
     * @param key 图片键值
     */
    @At("/oss/res")
    public View showOssResources(@Param("key") String key) {
        String suffix = getKeySuffix(key);
        String contentType = contentTypeMap.get(suffix);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        OssView view = new OssView(contentType);
        view.setKeyValue(key);

        return view;
    }

    /**
     * 显示各种资源
     * 
     * @param key 图片键值
     */
    @At("/oss/download")
    public View downloadOssResources(@Param("key") String key) {
        String suffix = getKeySuffix(key);
        String contentType = contentTypeMap.get(suffix);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        OssView view = new OssView(contentType);
        view.setKeyValue(key);
        view.setAttachFileName(this.getExtractFileName(key));

        return view;
    }

    /**
     * 显示各种资源
     *
     * @param key 图片键值
     */
    @At("/oss/mobi/download")
    public View downloadOssMobiResources(@Param("key") String key) {
        String suffix = getKeySuffix(key);
        String contentType = contentTypeMap.get(suffix);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        OssView view = new OssView(contentType);
        view.setKeyValue(key);
        view.setAttachFileName(this.getExtractFileName(key));

        return view;
    }

    /**
     * 资源上传
     *
     * @param request request
     * @param response response
     * @throws Exception e
     */
    @At("/oss/image/common")
    @Ok("json")
    public String attachmentUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String key = UUID.randomUUID().toString().replace("-", "") + ".jpg";

        OssUploader.uploadImage(0, key, request.getInputStream());

        return key;
    }

    // /**
    // * 图片上传到OSS，包含参数：name 文件名, fileGuid:文件名前缀
    // *
    // * @param request request
    // * @param response response
    // * @throws Exception e
    // */
    // @At("/oss/uploader")
    // @Ok("json")
    // public JSONObject attachmentUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
    // request.setCharacterEncoding("UTF-8");
    // response.setCharacterEncoding("UTF-8");
    // String fileName = request.getParameter("name");
    //
    // OssUploader.uploadImage(UserManager.getCurrentUser().getTenantId(),
    // request.getParameter("fileGuid") + "/" + fileName,
    // request.getInputStream());
    //
    // JSONObject json = new JSONObject();
    // json.put("url", request.getParameter("fileGuid") + File.separator + fileName);
    // json.put("filename", fileName);
    //
    // return json;
    // }

    private String getKeySuffix(String key) {
        return key.substring(key.lastIndexOf(".") + 1);
    }

    /**
     * 抽取文件名
     * 
     * @param key 键值
     * @return 文件名
     */
    private String getExtractFileName(String key) {
        return key.substring(key.lastIndexOf("/") + 1);
    }
}
