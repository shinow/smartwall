package link.smartwall.cloud.forum.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table(value = "forum_attach_prop")
public class AttchProp extends ForumBaseEntity {
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

	public String getAttachGuid() {
		return attachGuid;
	}

	public void setAttachGuid(String attachGuid) {
		this.attachGuid = attachGuid;
	}

	public String getPhysicalFileName() {
		return physicalFileName;
	}

	public void setPhysicalFileName(String physicalFileName) {
		this.physicalFileName = physicalFileName;
	}

	public String getRealFileName() {
		return realFileName;
	}

	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}

	public String getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(String downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
}
