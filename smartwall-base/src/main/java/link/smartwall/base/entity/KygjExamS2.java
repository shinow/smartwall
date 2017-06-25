package link.smartwall.base.entity;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import link.smartwall.basemodel.BaseEntity;

@Table("kygj_exam_s2")
public class KygjExamS2 extends BaseEntity {
	@Column("caption")
	private String caption;
	@Column("is_publish")
	private boolean isPublish;
	@Column("create_time")
	private Date createTime;
	@Column("content")
	private String content;
	
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public boolean isPublish() {
		return isPublish;
	}
	public void setPublish(boolean isPublish) {
		this.isPublish = isPublish;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
