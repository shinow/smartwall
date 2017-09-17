package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_rank")
public class Rank {
	@Column(value = "rank_guid")
	@Name
	private String guid;

	@Column(value = "rank_name")
	private String name;

	@Column(value = "rank_min")
	private int rankMin;

	@Column(value = "rank_image")
	private String rankImage;

	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;
}
