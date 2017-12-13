package link.smartwall.cloud.medical.questionbank.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itekchina.cloud.common.ajax.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import link.smartwall.cloud.medical.questionbank.domain.User;
import link.smartwall.cloud.medical.questionbank.exception.ExamException;
import link.smartwall.cloud.medical.questionbank.service.QuestionBankService;

@Api(value = "/v1", tags = {"医学考试题库"})
@RestController
@RequestMapping("/v1")
public class QuestionBankControll {
    @Autowired
    private QuestionBankService questionBankService;

    @ApiOperation(value = "所有分类")
    @RequestMapping(value = "/list/kind", method = RequestMethod.POST)
    public Object kind() {
        return questionBankService.getKinds();
    }

    @ApiOperation(value = "根据分类获取种类")
    @RequestMapping(value = "/list/catagory", method = RequestMethod.POST)
    public Object category(@RequestParam("kind_guid") String kindGuid) {
        return questionBankService.getCategorys(kindGuid);
    }

    @ApiOperation(value = "获取所有种类")
    @RequestMapping(value = "/list/all_catagory", method = RequestMethod.POST)
    public Object callCategory() {
        return questionBankService.getAllCategorys();
    }

    @ApiOperation(value = "根据种类获取科目")
    @RequestMapping(value = "/list/subject", method = RequestMethod.POST)
    public Object subject(@RequestParam("category_guid") String categoryGuid) {
        return questionBankService.getSubjects(categoryGuid);
    }

    @ApiOperation(value = "根据科目获取章节")
    @RequestMapping(value = "/list/chapter", method = RequestMethod.POST)
    public Object chapter(@RequestParam("subject_guid") String subjectGuid) {
        return questionBankService.getChapters(subjectGuid);
    }

    @ApiOperation(value = "新增分类")
    @RequestMapping(value = "/add/kind", method = RequestMethod.POST)
    public String saveKind(@RequestParam("name") String name) {
        return questionBankService.saveKind(name);
    }

    @ApiOperation(value = "新增类别")
    @RequestMapping(value = "/add/category", method = RequestMethod.POST)
    public String saveCategory(@RequestParam("name") String name, @RequestParam("kind_guid") String kindGuid) {
        return questionBankService.saveCategory(name, kindGuid);
    }

    @ApiOperation(value = "新增科目")
    @RequestMapping(value = "/add/subject", method = RequestMethod.POST)
    public String saveSubject(@RequestParam("name") String name, @RequestParam("category_guid") String categoryGuid) {
        return questionBankService.saveSubject(name, categoryGuid);
    }

    @ApiOperation(value = "新增章节")
    @RequestMapping(value = "/add/chapter", method = RequestMethod.POST)
    public String saveChapter(@RequestParam("name") String name, @RequestParam("subject_guid") String subjectGuid) {
        return questionBankService.saveChapter(name, subjectGuid);
    }

    @ApiOperation(value = "删除分类")
    @RequestMapping(value = "/del/kind", method = RequestMethod.POST)
    public int delKind(@RequestParam("guid") String guid) {
        return questionBankService.delKind(guid);
    }

    @ApiOperation(value = "删除类别")
    @RequestMapping(value = "/del/category", method = RequestMethod.POST)
    public int delCategory(@RequestParam("guid") String guid) {
        return questionBankService.delCategory(guid);
    }

    @ApiOperation(value = "删除科目")
    @RequestMapping(value = "/del/subject", method = RequestMethod.POST)
    public int delSubject(@RequestParam("guid") String guid) {
        return questionBankService.delSubject(guid);
    }

    @ApiOperation(value = "删除章节")
    @RequestMapping(value = "/del/chapter", method = RequestMethod.POST)
    public int delChapter(@RequestParam("guid") String guid) {
        return questionBankService.delChapter(guid);
    }

    @ApiOperation(value = "保存Chapter试卷")
    @RequestMapping(value = "/question/chapter/save", method = RequestMethod.POST)
    public String saveChapterQesution(@RequestParam("index") int index,
                                   @RequestParam("guid") String guid,
                                   @RequestParam("category_guid") String categoryGuid,
                                   @RequestParam("subject_guid") String subjectGuid,
                                   @RequestParam("chapter_guid") String chapterGuid,
                                   @RequestParam("data") String data) {
        String oid = guid;
        if ("0".equals(oid)) {
            oid = UUID.randomUUID().toString().replace("-", "");
        }
        questionBankService.saveChapterQuestion(index, oid, categoryGuid, subjectGuid, chapterGuid, data);

        return oid;
    }

    @ApiOperation(value = "获取Chapter试卷")
    @RequestMapping(value = "/question/chapter/get", method = RequestMethod.POST)
    public Object getChapterQesution(@RequestParam("chapter_guid") String chapterGuid) {
        return questionBankService.getChapterQuestion(chapterGuid);
    }

    @ApiOperation(value = "获取Chapter试卷,完整对象，手机端使用")
    @RequestMapping(value = "/question/chapter/get2", method = RequestMethod.POST)
    public Object getChapterQesution2(@RequestParam("chapter_guid") String chapterGuid) {
        return questionBankService.getChapterQuestion2(chapterGuid);
    }

    @ApiOperation(value = "保存Subject试卷")
    @RequestMapping(value = "/question/subject/save", method = RequestMethod.POST)
    public int saveSubjectQesution(@RequestParam("subject_guid") String subjectGuid,
                                   @RequestParam("data") String data) {
        questionBankService.saveSubjectQuestion(subjectGuid, data);

        return 0;
    }

    @ApiOperation(value = "保存Subject试卷")
    @RequestMapping(value = "/question/subject/get", method = RequestMethod.POST)
    public Object getSubjectQesution(@RequestParam("subject_guid") String subjectGuid) {
        return questionBankService.getSubjectQuestion(subjectGuid);
    }

    @ApiOperation(value = "获取用户关注考试GUID")
    @RequestMapping(value = "/user/category/get", method = RequestMethod.POST)
    public Object getUserCategory(@RequestParam("user_guid") String userGuid) {
        return questionBankService.getUserCategory(userGuid);
    }

    @ApiOperation(value = "获取用户关注考试内容")
    @RequestMapping(value = "/user/categoryObject/get", method = RequestMethod.POST)
    public Object getUserCategoryObject(@RequestParam("user_guid") String userGuid) {
        return questionBankService.getUserCategoryObject(userGuid);
    }

    @ApiOperation(value = "保存用户关注考试")
    @RequestMapping(value = "/user/category/set", method = RequestMethod.POST)
    public Object setUserCategory(@RequestParam("user_guid") String userGuid,
                                  @RequestParam("category_guid") String categoryGuid) {
        return questionBankService.setUserCategory(userGuid, categoryGuid);
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public Result register(User user) {
        try {
            questionBankService.register(user);
            return Result.successResult(user);
        } catch (ExamException ex) {
            return Result.failureResult(ex.getMessage());
        }
    }

    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Result login(@RequestParam("mobile") String mobile, @RequestParam("password") String password) {
        try {
            User user = questionBankService.login(mobile, password);
            return Result.successResult(user);
        } catch (ExamException ex) {
            return Result.failureResult(ex.getMessage());
        }
    }

    @ApiOperation(value = "保存用户考试类型")
    @RequestMapping(value = "/user/kind_category", method = RequestMethod.POST)
    public String saveUserKindCategory(@RequestParam("guid") String guid,
                                       @RequestParam("kind") String kind,
                                       @RequestParam("category") String category) {
        try {
            return questionBankService.saveUserKindCategory(guid, kind, category);
        } catch (ExamException ex) {
            ex.printStackTrace();

            return null;
        }
    }
}
