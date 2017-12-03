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
