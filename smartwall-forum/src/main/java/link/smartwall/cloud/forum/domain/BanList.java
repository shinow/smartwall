package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_banlist")
public class BanList {
	@Column(value = "banlist_guid")
	@Name
	private String guid;

	@Column(value = "user_guid")
	private String userGuid;

	@Column(value = "reason")
	private String reason;

	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;
}
