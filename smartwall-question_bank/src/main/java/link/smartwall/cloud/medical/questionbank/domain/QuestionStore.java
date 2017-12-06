package link.smartwall.cloud.medical.questionbank.domain;

import java.util.Date;

/**
 * 试题笔记
 *
 */
public class QuestionStore {
	private String userGuid;
	private String questionGuid;
	private Date dataTime;

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

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
}
