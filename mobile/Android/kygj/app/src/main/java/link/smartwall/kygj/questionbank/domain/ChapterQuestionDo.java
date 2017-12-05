package link.smartwall.kygj.questionbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.http.annotation.HttpResponse;

import link.smartwall.kygj.questionbank.http.JsonResponseParser;

/**
 * 用户所做题目
 */
@Table(name = "exam_chapter_question_do")
@HttpResponse(parser = JsonResponseParser.class)
public class ChapterQuestionDo {
    @Column(name = "question_guid", isId = true)
    private String questionGuid;

    @Column(name = "chapterGuid")
    private String chapterGuid;

    @Column(name = "result")
    private int result;

    public String getQuestionGuid() {
        return questionGuid;
    }

    public void setQuestionGuid(String questionGuid) {
        this.questionGuid = questionGuid;
    }

    public String getChapterGuid() {
        return chapterGuid;
    }

    public void setChapterGuid(String chapterGuid) {
        this.chapterGuid = chapterGuid;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
