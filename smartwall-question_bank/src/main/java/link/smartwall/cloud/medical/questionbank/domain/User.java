package link.smartwall.cloud.medical.questionbank.domain;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.EL;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.Table;

/**
 * 用户
 *
 */
@Table(value = "exam_user")
public class User {
	@Column(value = "guid")
	@Name
	@Prev(els = @EL("uuid(32)"))
	private String guid;

	/**
	 * 手机号
	 */
	@Column(value = "mobile")
	private String mobile;

	/**
	 * 名称
	 */
	@Column(value = "name")
	private String name;

	/**
	 * 密码
	 */
	@Column(value = "password")
	private String password;

	/**
	 * 考试种类
	 */
	@Column(value = "exam_kind")
	private String examKind;

	/**
	 * 考试类别
	 */
	@Column(value = "exam_category")
	private String examCategory;

	/**
	 * 注册时间
	 */
	@Column(value = "reg_time")
	private Date regTime;
	/**
	 * 更新时间
	 */
	@Column(value = "modify_time")
	private Date modifyTime;

	/**
	 * 个人图像
	 */
	@Column(value = "avator")
	private String avator;

	/**
	 * 注册码
	 */
	private String verifyCode;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getExamKind() {
		return examKind;
	}

	public void setExamKind(String examKind) {
		this.examKind = examKind;
	}

	public String getExamCategory() {
		return examCategory;
	}

	public void setExamCategory(String examCategory) {
		this.examCategory = examCategory;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getAvator() {
		return avator;
	}

	public void setAvator(String avator) {
		this.avator = avator;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
}
