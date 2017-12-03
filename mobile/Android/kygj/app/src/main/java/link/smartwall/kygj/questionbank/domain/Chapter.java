package link.smartwall.kygj.questionbank.domain;

import org.xutils.db.annotation.Table;
import org.xutils.http.annotation.HttpResponse;

import link.smartwall.kygj.questionbank.http.JsonResponseParser;

/**
 * 考试章节
 */
@Table(name = "exam_chapter")
@HttpResponse(parser = JsonResponseParser.class)
public class Chapter extends BaseItem {
    @Override
    public int getType() {
        return Types.TYPE_CHAPTER;
    }
}
