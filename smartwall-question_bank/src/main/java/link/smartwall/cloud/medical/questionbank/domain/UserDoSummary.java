package link.smartwall.cloud.medical.questionbank.domain;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

/**
 * 题目答题汇总信息
 */
@Table(value = "exam_user_do_summary")
public class UserDoSummary {
	@Column(value = "guid")
	@Name
	@Prev(els = @EL("uuid(32)"))
	private String guid;

	/**
	 * 试题Guid
	 */
	@Column(value = "question_guid")
	private String questionGuid;

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
	 * 总答题次数
	 */
	@Column(value = "do_num")
	private int doNum;

	/**
	 * 正确次数
	 */
	@Column(value = "right_num")
	private int rightNum;

	/**
	 * 错误次数
	 */
	@Column(value = "error_num")
	private int errorNum;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getQuestionGuid() {
		return questionGuid;
	}

	public void setQuestionGuid(String questionGuid) {
		this.questionGuid = questionGuid;
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

	public int getDoNum() {
		return doNum;
	}

	public void setDoNum(int doNum) {
		this.doNum = doNum;
	}

	public int getRightNum() {
		return rightNum;
	}

	public void setRightNum(int rightNum) {
		this.rightNum = rightNum;
	}

	public int getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}
}
