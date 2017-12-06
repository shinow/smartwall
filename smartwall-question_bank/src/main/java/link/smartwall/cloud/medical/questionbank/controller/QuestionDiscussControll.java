package link.smartwall.cloud.medical.questionbank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import link.smartwall.cloud.medical.questionbank.domain.QuestionDiscuss;
import link.smartwall.cloud.medical.questionbank.service.QuestionDiscussService;

@Api(value = "/v1", tags = { "试题评论" })
@RestController
@RequestMapping("/v1")
public class QuestionDiscussControll {
	@Autowired
	private QuestionDiscussService questionDiscussService;

	@ApiOperation(value = "新建评论")
	@RequestMapping(value = "/question/comment", method = RequestMethod.POST)
	public String newComment(QuestionDiscuss questionDiscuss) {
		questionDiscussService.insertQuestionDiscuss(questionDiscuss);

		return "SUCCESS";
	}
}
