package link.smartwall.cloud.medical.questionbank.domain;

/**
 * 做题统计
 *
 */
public class QuestionUserDoInfo {
	private String userGuid;
	private String questionGuid;
	private int total;
	private int right;
	private int error;

	public String getQuestionGuid() {
		return questionGuid;
	}

	public void setQuestionGuid(String questionGuid) {
		this.questionGuid = questionGuid;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

}
