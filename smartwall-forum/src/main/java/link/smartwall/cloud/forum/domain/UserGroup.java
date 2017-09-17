package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_user_group")
public class UserGroup {
	@Column(value = "user_guid")
	private String userGuid;

	@Column(value = "group_guid")
	private String groupGuid;

	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;
}
