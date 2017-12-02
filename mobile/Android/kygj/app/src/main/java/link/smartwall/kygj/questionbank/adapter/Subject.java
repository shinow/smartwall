package link.smartwall.kygj.questionbank.adapter;

import org.xutils.db.annotation.Table;

import java.util.Collections;
import java.util.List;

/**
 * 考试科目
 */
@Table(name = "exam_subject")
public class Subject extends BaseItem {
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
}
