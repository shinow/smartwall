package link.smartwall.kygj.questionbank.domain;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.http.annotation.HttpResponse;

import java.util.Date;

import link.smartwall.kygj.questionbank.http.JsonResponseParser;

/**
 * 用户信息
 */
@Table(name = "exam_user")
@HttpResponse(parser = JsonResponseParser.class)
public abstract class UserInfo {
    @Column(name = "guid", isId = true)
    private String guid;

    /**
     * 手机号
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 考试种类
     */
    @Column(name = "exam_kind")
    private String examKind;

    /**
     * 考试类别
     */
    @Column(name = "exam_category")
    private String examCategory;

    /**
     * 更新时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 个人图像
     */
    @Column(name = "avator")
    private String avator;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExamKind() {
        return examKind;
    }

    public void setExamKind(String examKind) {
        this.examKind = examKind;
    }

    public String getExamCategory() {
        return examCategory;
    }

    public void setExamCategory(String examCategory) {
        this.examCategory = examCategory;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }
}
