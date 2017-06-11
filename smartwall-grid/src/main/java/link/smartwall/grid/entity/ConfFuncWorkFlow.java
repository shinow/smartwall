package link.smartwall.grid.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.itfsm.basemodel.AbstractGuidObject;

/**
 * 流程配置实体对象
 */
@Table("conf_func_workflow")
public class ConfFuncWorkFlow extends AbstractGuidObject {
    /**
     * 标题
     */
    @Column
    private String caption;
    /**
     * 启用日期
     */
    @Column
    private String startTime;
    /**
     * 停用日期
     */
    @Column
    private String endTime;
    /**
     * 备注
     */
    @Column
    private String remark;
    /**
     * 移动表单
     */
    @Column
    private String mobi_form_guid;
    /**
     * Web表单
     */
    @Column
    private String web_form_guid;
    /**
     * 流程guid
     */
    @Column
    private String workflow_guid;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMobi_form_guid() {
        return mobi_form_guid;
    }

    public void setMobi_form_guid(String mobi_form_guid) {
        this.mobi_form_guid = mobi_form_guid;
    }

    public String getWeb_form_guid() {
        return web_form_guid;
    }

    public void setWeb_form_guid(String web_form_guid) {
        this.web_form_guid = web_form_guid;
    }

    public String getWorkflow_guid() {
        return workflow_guid;
    }

    public void setWorkflow_guid(String workflow_guid) {
        this.workflow_guid = workflow_guid;
    }
}
