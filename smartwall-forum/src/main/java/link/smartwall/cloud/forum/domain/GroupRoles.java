package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_group_roles")
public class GroupRoles {
	@Column(value = "group_guid")
	@Name
	private String guid;

	@Column(value = "role_guid")
	private String roleGuid;

	@Column(value = "role_name")
	private String roleName;

	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;
}
