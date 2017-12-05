package link.smartwall.cloud.medical.questionbank.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

/**
 * 用户做题信息
 */
@Table(value = "exam_user_question_do")
public class UserQuestionDo {
	@Column(value = "guid")
	@Name
	@Prev(els = @EL("uuid(32)"))
	private String guid;

	/**
	 * 用户Guid
	 */
	@Column(value = "user_guid")
	private String userGuid;

	/**
	 * 试题所属章节
	 */
	@Column(value = "chapter_guid")
	private String chapterGuid;
	/**
	 * 试题编号
	 */
	@Column(value = "question_no")
	private int no;

	/**
	 * 试题Guid
	 */
	@Column(value = "question_guid")
	private String questionGuid;

	/**
	 * 答题状态,0：未答题， 1：正确， 2：错误
	 */
	@Column(value = "status")
	private int status;
	/**
	 * 答题时间
	 */
	@Column(value = "data_time")
	private Date dataTime;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public String getChapterGuid() {
		return chapterGuid;
	}

	public void setChapterGuid(String chapterGuid) {
		this.chapterGuid = chapterGuid;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getQuestionGuid() {
		return questionGuid;
	}

	public void setQuestionGuid(String questionGuid) {
		this.questionGuid = questionGuid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
}
