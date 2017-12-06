package link.smartwall.kygj.questionbank.domain;

import org.xutils.http.annotation.HttpResponse;

import java.util.Date;

import link.smartwall.kygj.questionbank.http.JsonResponseParser;


/**
 * 试题评论信息（Copy from 后台代码）
 *
 */
@HttpResponse(parser = JsonResponseParser.class)
public class QuestionDiscuss {
	private String questionGuid;
	private String userGuid;
	private String userName;
	private String comment;
	private String commentTime;
	private String replierGuid;
	private String replierName;
	private String replierComment;
	private Date replierTime;
	private int likes;
	private Date dataTime;

	public String getQuestionGuid() {
		return questionGuid;
	}

	public void setQuestionGuid(String questionGuid) {
		this.questionGuid = questionGuid;
	}

	public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getReplierGuid() {
		return replierGuid;
	}

	public void setReplierGuid(String replierGuid) {
		this.replierGuid = replierGuid;
	}

	public String getReplierName() {
		return replierName;
	}

	public void setReplierName(String replierName) {
		this.replierName = replierName;
	}

	public String getReplierComment() {
		return replierComment;
	}

	public void setReplierComment(String replierComment) {
		this.replierComment = replierComment;
	}

	public Date getReplierTime() {
		return replierTime;
	}

	public void setReplierTime(Date replierTime) {
		this.replierTime = replierTime;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public Date getDataTime() {
		return dataTime;
	}

	public void setDataTime(Date dataTime) {
		this.dataTime = dataTime;
	}
}
