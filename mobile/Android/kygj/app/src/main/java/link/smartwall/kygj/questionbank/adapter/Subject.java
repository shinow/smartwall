package link.smartwall.kygj.questionbank.adapter;

import java.util.List;

/**
 * 考试科目
 */

public class Subject extends BaseItem {
    @Override
    public int getType() {
        return Types.TYPE_SUBJECT;
    }

    private List<Chapter> chapters;

    private Chapter chapter;

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }
}
