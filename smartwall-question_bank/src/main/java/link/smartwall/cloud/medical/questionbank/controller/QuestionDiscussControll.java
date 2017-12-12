package link.smartwall.cloud.medical.questionbank.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import link.smartwall.cloud.medical.questionbank.domain.QuestionDiscuss;
import link.smartwall.cloud.medical.questionbank.exception.ExamException;
import link.smartwall.cloud.medical.questionbank.service.QuestionDiscussService;

@Api(value = "/v1", tags = {"试题评论"})
@RestController
@RequestMapping("/v1")
public class QuestionDiscussControll {
    @Autowired
    private QuestionDiscussService questionDiscussService;

    @ApiOperation(value = "新建评论")
    @RequestMapping(value = "/question/comment", method = RequestMethod.POST)
    public String newComment(QuestionDiscuss questionDiscuss) {
        questionDiscuss.setCommentTime(new Date());
        questionDiscuss.setDataTime(new Date());

        questionDiscussService.insertQuestionDiscuss(questionDiscuss);

        return "SUCCESS";
    }

    @ApiOperation(value = "新建评论")
    @RequestMapping(value = "/question/reply_comment", method = RequestMethod.POST)
    public String replayComment(QuestionDiscuss questionDiscuss) {
        questionDiscuss.setReplierTime(new Date());
        questionDiscuss.setDataTime(new Date());

        questionDiscussService.insertQuestionDiscuss(questionDiscuss);

        return "SUCCESS";
    }

    @ApiOperation(value = "获取所有评论")
    @RequestMapping(value = "/question/list_comment", method = RequestMethod.POST)
    public List<QuestionDiscuss> listComment(@RequestParam("question_guid") String questionGuid,
                                             @RequestParam("page") int page) {
        return questionDiscussService.getQuestionDiscuss(questionGuid, page);
    }

    @ApiOperation(value = "获取试题评论数")
    @RequestMapping(value = "/question/comment_count", method = RequestMethod.POST)
    public Object getQestionCommentCount(@RequestParam("question_guid") String questionGuid) {
        return questionDiscussService.getQestionCommentCount(questionGuid);
    }

    @ApiOperation(value = "保存用户答题信息")
    @RequestMapping(value = "/question/do_info/set", method = RequestMethod.POST)
    public String saveUserQeustionDoInfo(@RequestParam("user_guid") String userGuid,
                                         @RequestParam("question_guid") String questionGuid,
                                         @RequestParam("chapter_guid") String chapterGuid,
                                         @RequestParam("answer") String answer,
                                         @RequestParam("result") int result) {
        try {
            questionDiscussService.saveQuestionDoInfo(userGuid, questionGuid, chapterGuid, answer, result);
        } catch (ExamException ex) {
            ex.printStackTrace();
        }

        return "SUCCESS";
    }

    @ApiOperation(value = "获取答题信息")
    @RequestMapping(value = "/question/do_info/get", method = RequestMethod.POST)
    public Object getUserQeustionDoInfo(@RequestParam("user_guid") String userGuid,
                                        @RequestParam("question_guid") String questionGuid) {
        return questionDiscussService.getQuestionDoInfo(userGuid, questionGuid);
    }

    @ApiOperation(value = "收藏题目")
    @RequestMapping(value = "/question/likes/set", method = RequestMethod.POST)
    public Object setQuestionLikes(@RequestParam("user_guid") String userGuid,
                                   @RequestParam("question_guid") String questionGuid) {
        questionDiscussService.setQuestionLikes(userGuid, questionGuid);
        
        return "SUCCESS";
    }
}
