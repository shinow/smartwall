package link.smartwall.grid.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.itfsm.basemodel.BaseEntity;
/**
 * 下拉数据
 * @author caisj
 *
 */
@Table("CONF_DATASET_SQL")
public class DataSetSql extends BaseEntity{

	/**
	 * 代码
	 */
	@Column
	private String code;
	/**
	 * 中文名称
	 */
	@Column
	private String caption;
	/**
	 * 数据源配置
	 */
	@Column
	private String content;
	/**
	 * 输出字段
	 */
	@Column
	private String fields;
	/**
	 * 排序字段
	 */
	@Column
	private String order_info;
	/**
	 * 输入参数
	 */
	@Column
	private String parameters;
	/**
	 * 输入变量
	 */
	@Column
	private String vars;
	/**
	 * 备注
	 */
	@Column
	private String remark;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public String getOrder_info() {
		return order_info;
	}
	public void setOrder_info(String order_info) {
		this.order_info = order_info;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getVars() {
		return vars;
	}
	public void setVars(String vars) {
		this.vars = vars;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
