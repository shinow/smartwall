package link.smartwall.cloud.medical.questionbank.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 文件服务Controller
 */
@Api(value = "/", tags = { "题库管理" })
@Controller
@RequestMapping("/")
public class WebController {
	@ApiOperation(value = "设计器")
	@RequestMapping(value = "designer", method = RequestMethod.GET)
	public String designer(Map<String, Object> map) {
		return "/designer";
	}

	@ApiOperation(value = "基本信息")
	@RequestMapping(value = "base_info", method = RequestMethod.GET)
	public String baseInfo(Map<String, Object> map) {
		return "/base_info";
	}

	@ApiOperation(value = "选择章节")
	@RequestMapping(value = "select_chapter", method = RequestMethod.GET)
	public String selectChapter(Map<String, Object> map) {
		return "/select_chapter";
	}

	@ApiOperation(value = "开始考试")
	@RequestMapping(value = "start_exam", method = RequestMethod.GET)
	public String startExam(Map<String, Object> map) {
		return "/start_exam";
	}
}