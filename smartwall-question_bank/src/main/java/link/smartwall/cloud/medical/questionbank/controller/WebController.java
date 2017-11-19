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

	@ApiOperation(value = "设计器")
	@RequestMapping(value = "base_info", method = RequestMethod.GET)
	public String tenant(Map<String, Object> map) {
		return "/base_info";
	}
}