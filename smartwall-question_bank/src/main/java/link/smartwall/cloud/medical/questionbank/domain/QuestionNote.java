package link.smartwall.cloud.medical.questionbank.domain;

import java.util.Date;

/**
 * 试题笔记
 *
 */
public class QuestionNote {
	private String userGuid;
	private String questionGuid;
	private String note;
	private Date dateTime;

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public String getQuestionGuid() {
		return questionGuid;
	}

	public void setQuestionGuid(String questionGuid) {
		this.questionGuid = questionGuid;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
}
