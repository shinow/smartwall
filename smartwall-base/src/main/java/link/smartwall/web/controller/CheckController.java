package link.smartwall.web.controller;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import link.smartwall.service.CheckService;

/**
 * 主页面
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-8-13 lexloo
 *        </pre>
 */
@IocBean
@At("/check/custom")
public class CheckController {
	@Inject
	private CheckService checkService;
	
    @At("/checkCode")
    @Ok("json")
    public Boolean getDataByGuid(@Param("bizCode") String bizCode,@Param("code") String code,@Param("guid") String guid) {
    	return checkService.checkCode(bizCode,code,guid);
    }

}