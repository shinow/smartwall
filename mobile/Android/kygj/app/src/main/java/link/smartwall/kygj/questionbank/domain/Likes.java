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

    @Column(name = "value")
    private String value;

    public String getQuestionGuid() {
        return questionGuid;
    }

    public void setQuestionGuid(String questionGuid) {
        this.questionGuid = questionGuid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}