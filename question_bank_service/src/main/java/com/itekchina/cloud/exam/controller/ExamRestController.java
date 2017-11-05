package com.itekchina.cloud.exam.controller;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itekchina.cloud.common.ajax.Result;
import com.itekchina.cloud.exam.service.ExamService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 文件共享控制器
 * 
 * @author <a herf="wangbo@infinitek.net">wangbo</a>
 * @version 1.0
 * @since 2017-09-21，16:50
 */
@Api(value = "/v1", tags = {"考试相关"})
@RestController
@RequestMapping("/v1")
public class ExamRestController {
    @Autowired
    private ExamService examService;

    @ApiOperation(value = "获取试卷列表")
    @RequestMapping(value = "/paper_list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Result paperList(@RequestParam(value = "tenant_id") int tenantId) {
        return Result.successResult(examService.getPaperList(tenantId));
    }

    @ApiOperation(value = "获取试卷")
    @RequestMapping(value = "/paper", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Result getPaper(@RequestParam(value = "tenant_id") int tenantId, @RequestParam(value = "guid") String guid) {
        return Result.successResult(examService.getPaper(tenantId, guid));
    }

    @ApiOperation(value = "保存试卷")
    @RequestMapping(value = "/paper", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Result savePaper(@RequestParam(value = "tenant_id") int tenantId,
                            @RequestParam(value = "guid", required = false) String guid,
                            @RequestParam(value = "data") String data) {
        return Result.successResult(examService.savePaper(tenantId, guid, data));
    }

    @ApiOperation(value = "获取答题结果")
    @RequestMapping(value = "/answer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Result getAnswer(@RequestParam(value = "emp_id") int empId,
                            @RequestParam(value = "exam_guid") String examGuid) {
        return Result.successResult(examService.getAnswer(empId, examGuid));
    }

    @ApiOperation(value = "提交答题")
    @RequestMapping(value = "/answer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public Result saveAnswer(@RequestParam(value = "exam_guid") String examGuid,
                             @RequestParam(value = "choice_score") int choiceScore,
                             @RequestParam(value = "scoring_score") int scoringScore,
                             @RequestParam(value = "tenant_id") int tenantId,
                             @RequestParam(value = "emp_id") int empId,
                             @RequestParam(value = "emp_name") String empName,
                             @RequestParam(value = "tenant_name") String tenantName,
                             @RequestParam(value = "guid", required = false) String guid,
                             @RequestParam(value = "data") String data) {
        return Result.successResult(examService.saveAnswer(examGuid,
                                                           choiceScore,
                                                           scoringScore,
                                                           tenantId,
                                                           empId,
                                                           empName,
                                                           tenantName,
                                                           guid,
                                                           data));
    }

    @ApiOperation(value = "试题排名")
    @RequestMapping(value = "/rank", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Result getRank(@RequestParam(value = "exam_guid") String examGuid) {
        return Result.successResult(examService.getRank(examGuid));
    }

    @ApiOperation(value = "我的排名")
    @RequestMapping(value = "/rank/mine", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public Result getRankMine(@RequestParam(value = "emp_id") int empId,
                              @RequestParam(value = "exam_guid") String examGuid) {
        return Result.successResult(examService.getRankMine(empId, examGuid));
    }

}
