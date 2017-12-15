package link.smartwall.kygj.questionbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.http.annotation.HttpResponse;

import link.smartwall.kygj.questionbank.http.JsonResponseParser;

/**
 * 章节题目
 */
@Table(name = "exam_chapter_questions")
@HttpResponse(parser = JsonResponseParser.class)
public class ChapterQuestions {
    @Column(name = "guid", isId = true)
    private String guid;

    @Column(name = "index")
    private String index;

    @Column(name = "categoryGuid")
    private String categoryGuid;

    @Column(name = "subjectGuid")
    private String subjectGuid;

    @Column(name = "chapterGuid")
    private String chapterGuid;

    @Column(name = "data")
    private String data;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCategoryGuid() {
        return categoryGuid;
    }

    public void setCategoryGuid(String categoryGuid) {
        this.categoryGuid = categoryGuid;
    }

    public String getSubjectGuid() {
        return subjectGuid;
    }

    public void setSubjectGuid(String subjectGuid) {
        this.subjectGuid = subjectGuid;
    }

    public String getChapterGuid() {
        return chapterGuid;
    }

    public void setChapterGuid(String chapterGuid) {
        this.chapterGuid = chapterGuid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
