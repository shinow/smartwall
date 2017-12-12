package link.smartwall.kygj.questionbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 收藏
 */
@Table(name = "exam_likes")
public class Likes {
    @Column(name = "questionGuid", isId = true)
    private String questionGuid;

    public String getQuestionGuid() {
        return questionGuid;
    }

    public void setQuestionGuid(String questionGuid) {
        this.questionGuid = questionGuid;
    }
}