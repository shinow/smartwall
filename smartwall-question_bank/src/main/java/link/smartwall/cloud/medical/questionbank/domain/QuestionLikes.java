package link.smartwall.cloud.medical.questionbank.domain;

/**
 * 收藏
 *
 */
public class QuestionLikes {
    private String userGuid;
    private String questionGuid;

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
}
