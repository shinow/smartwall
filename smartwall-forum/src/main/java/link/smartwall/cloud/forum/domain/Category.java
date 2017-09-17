package link.smartwall.cloud.forum.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_category")
public class Category extends ForumBaseEntity {
	@Column(value = "title")
	private String title;

	@Column(value = "display_order")
	private String displayOrder;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
}
