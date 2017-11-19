package link.smartwall.cloud.medical.questionbank.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

/**
 * 章节试题
 *
 */
@Table(value = "exam_medical_chapter_question")
public class ChapterQuestion {
	@Column(value = "guid")
	@Name
	@Prev(els = @EL("uuid(32)"))
	private String guid;

	@Column(value = "chapter_guid")
	private String chapterGuid;
	
	@Column(value = "data")
	private String data;

	@Column(value = "modify_time")
	private Date modifyTime;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getChapterGuid() {
		return chapterGuid;
	}

	public void setChapterGuid(String chapterGuid) {
		this.chapterGuid = chapterGuid;
	}
}
