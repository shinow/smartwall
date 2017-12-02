package link.smartwall.kygj.questionbank.adapter;

import org.xutils.db.annotation.Table;

/**
 * 考试章节
 */
@Table(name = "exam_chapter")
public class Chapter extends BaseItem {
    @Override
    public int getType() {
        return Types.TYPE_CHAPTER;
    }
}
