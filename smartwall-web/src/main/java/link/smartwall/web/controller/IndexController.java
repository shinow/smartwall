package link.smartwall.web.controller;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * 主页面
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-8-13 lexloo
 *        </pre>
 */
@IocBean
public class IndexController {
    /**
     * sfa登录页面
     * 
     * @param request request请求
     * @return 页面参数
     */
    @At("/index")
    @Ok("fm:/index/index.html")
    public void sfaIndex() {} 
}
