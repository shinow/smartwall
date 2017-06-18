/**
 * SmartWall(2013)
 */
package link.smartwall.web.controller.exam;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import link.smartwall.service.exam.ExamService;

/**
 * Grid对象，MVC框架支持对象
 * 
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @version 1.0
 * @since 2.0
 *        <p/>
 * 
 *        <pre>
 * 历史：
 * 建立: 2013-8-13 lexloo
 *        </pre>
 */
@IocBean
public class ExamController {
	@Inject
	private ExamService examService;

	/**
	 * 网格显示，显示第一页
	 * 
	 * @param grid
	 *            网格配置
	 * @return 页面
	 */
	@At("/exam/edit")
	@Ok("fm:/exam/edit.html")
	public Map<String, Object> edit(@Param("type") String type, @Param("guid") String guid) {
		Map<String, Object> rtn = new HashMap<>();
		rtn.put("type", type);
		rtn.put("guid", guid);

		return rtn;
	}
}