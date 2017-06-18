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

import link.smartwall.base.api.Result;
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
	 * 编辑试卷
	 */
	@At("/exam/edit")
	@Ok("fm:/exam/edit.html")
	public Map<String, Object> edit(@Param("type") String type, @Param("guid") String guid) {
		Map<String, Object> rtn = new HashMap<>();
		rtn.put("type", type);
		rtn.put("guid", guid);

		return rtn;
	}

	/**
	 * 保存试卷
	 */
	@At("/exam/save")
	@Ok("json")
	public Result save(@Param("type") String type, @Param("guid") String guid, @Param("value") String value) {
		examService.saveExam(type, guid, value);

		return Result.successResult();
	}

	/**
	 * 获取试卷
	 */
	@At("/exam/save")
	@Ok("json")
	public Result get(@Param("type") String type, @Param("guid") String guid) {
		return Result.successResult(examService.getExam(type, guid));
	}
}