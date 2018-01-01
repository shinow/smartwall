/**
 * smartwall
 */
package link.smartwall.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import com.alibaba.fastjson.JSONObject;

import link.smartwall.oss.OssUploader;

/**
 * 文件上传控制器
 * 
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @version 1.0
 * @since 外勤管家3.0
 *        <p/>
 * 
 *        <pre>
 * 历史：
 *      建立: 2014年9月23日 lexloo
 *        </pre>
 */
@IocBean
public class ImageUploaderController {
    /**
     * 公告附件上传
     *
     * @param request request
     * @param response response
     * @throws Exception e
     */
    @At("/image/uploader")
    public void attachmentUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String fileName = request.getParameter("name");
        String fileGuid = request.getParameter("fileGuid");

        OssUploader.uploadImage(1, "images/" + fileGuid + "/" + fileName, request.getInputStream());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", fileGuid + File.separator + fileName);
        jsonObject.put("file_name", request.getParameter("name"));

        response.getWriter().print(jsonObject.toJSONString());
    }
}
