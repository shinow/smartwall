/**
 * SmartWall(2013)
 */
package link.smartwall.web.controller.exam;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import link.smartwall.base.api.Result;
import link.smartwall.service.exam.ExamService;

/**
 * 考试手机端接口
 */
@At("/mobi")
@IocBean
public class ExamMobileController {
	@Inject
	private ExamService examService;

	/**
	 * 获取试卷列表
	 */
	@At("/exam/get_questions")
	@Ok("json")
	public Result getQuestions(@Param("type") String type) {
		return Result.successResult(examService.getQuestionsList(type));
	}


	/**
	 * 获取试卷
	 */
	@At("/exam/get")
	@Ok("json")
	public Result get(@Param("type") String type, @Param("guid") String guid) {
		return Result.successResult(examService.getExam(type, guid));
	}
}