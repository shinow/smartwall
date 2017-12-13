package link.smartwall.cloud.medical.questionbank.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;

/**
 * 章节试题
 *
 */
public class ChapterQuestion {
    @Id
    private String guid;
    private String index;
    private String categoryGuid;
    private String subjectGuid;
    private String chapterGuid;
    private String data;
    private Date modifyTime;

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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
