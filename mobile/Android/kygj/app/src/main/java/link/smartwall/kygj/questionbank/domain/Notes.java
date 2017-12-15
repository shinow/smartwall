package link.smartwall.kygj.questionbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 收藏
 */
@Table(name = "exam_notes")
public class Notes {
    @Column(name = "questionGuid", isId = true)
    private String questionGuid;

    @Column(name = "notes")
    private String notes;

    public String getQuestionGuid() {
        return questionGuid;
    }

    public void setQuestionGuid(String questionGuid) {
        this.questionGuid = questionGuid;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}