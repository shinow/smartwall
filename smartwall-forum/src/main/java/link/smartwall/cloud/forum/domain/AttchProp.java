package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_attach_prop")
public class AttchProp {
	@Column(value = "attach_prop_guid")
	@Name
	private String guid;

	@Column(value = "attach_guid")
	private String attachGuid;

	@Column(value = "physical_filename")
	private String physicalFileName;

	@Column(value = "real_filename")
	private String realFileName;

	@Column(value = "download_count")
	private String downloadCount;

	@Column(value = "description")
	private String description;

	@Column(value = "mimetype")
	private String mimeType;

	@Column(value = "filesize")
	private int fileSize;

	@Column(value = "upload_time")
	private Date uploadTime;

	@Column(value = "tenant_id")
	private int tenantId;

	@Column(value = "data_time")
	private Date dataTime;
}
