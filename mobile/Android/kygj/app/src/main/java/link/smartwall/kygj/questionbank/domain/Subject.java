package link.smartwall.kygj.questionbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.http.annotation.HttpResponse;

import java.util.Collections;
import java.util.List;

import link.smartwall.kygj.questionbank.http.JsonResponseParser;

/**
 * 考试科目
 */
@Table(name = "exam_subject")
@HttpResponse(parser = JsonResponseParser.class)
public class Subject extends BaseItem {
    @Column(name = "categoryGuid")
    private String categoryGuid;

    @Override
    public int getType() {
        return Types.TYPE_SUBJECT;
    }

    private List<Chapter> chapters;

    public List<Chapter> getChapters() {
        return chapters == null ? Collections.EMPTY_LIST: chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public String getCategoryGuid() {
        return categoryGuid;
    }

    public void setCategoryGuid(String categoryGuid) {
        this.categoryGuid = categoryGuid;
    }
}
