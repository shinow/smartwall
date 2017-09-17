package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_group")
public class Group {
	@Column(value = "group_guid")
	@Name
	private String guid;

	@Column(value = "group_name")
	private String name;

	@Column(value = "description")
	private String description;

	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;
}
