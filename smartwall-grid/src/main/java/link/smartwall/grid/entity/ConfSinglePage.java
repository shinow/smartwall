package link.smartwall.grid.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import com.itfsm.basemodel.BaseEntity;

/**
 * 超链接单页信息配置
 * @author caisj
 *
 */
@Table("CONF_SINGLE_PAGE")
public class ConfSinglePage  extends BaseEntity{

	/**
	 * html模板
	 */
	@Column
	private String html_template;
	/**
	 * 数据源配置
	 */
	@Column
	private String sql_content;
	/**
	 * 样式
	 */
	@Column
	private String css_style;
	@Column
	private String remark;
	
	public String getHtml_template() {
		return html_template;
	}
	public void setHtml_template(String html_template) {
		this.html_template = html_template;
	}
	public String getSql_content() {
		return sql_content;
	}
	public void setSql_content(String sql_content) {
		this.sql_content = sql_content;
	}
	public String getCss_style() {
		return css_style;
	}
	public void setCss_style(String css_style) {
		this.css_style = css_style;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
