package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_attach")
public class Attach {
	@Column(value = "attach_guid")
	@Name
	private String guid;

	@Column(value = "post_guid")
	private String postGuid;

	@Column(value = "user_guid")
	private String userGuid;

	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;
}
