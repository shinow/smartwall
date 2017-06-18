package link.smartwall.base.entity;

import org.apache.commons.lang.RandomStringUtils;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import link.smartwall.basemodel.AbstractObject;
import link.smartwall.basemodel.BaseEntity;

/**
 * 验证码
 *
 * @author <a herf="wangbo@infinitek.net">wangbo</a>
 * @version 1.0
 * @since 2017-05-08，16:35
 */
@Table("sfa_verification_code")
public class VerificationCode extends BaseEntity {

    @Column("mobile_no")
    private String mobile;

    @Column
    private String code;

    public VerificationCode() {
        this.code = createCodeString();
    }

    /**
     *
     * @param mobile mobile
     */
    public VerificationCode(String mobile) {
        this.mobile = mobile;
        this.code = createCodeString();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void beforeUpdate(Dao dao, AbstractObject prevObj) {
        super.beforeUpdate(dao, prevObj);
        this.code = createCodeString();
    }

    /**
     * @return 生成六位的随机验证码，由大小写字母和数字组成
     */
    private String createCodeString() {
//        return RandomStringUtils.random(6, "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789");
        return RandomStringUtils.random(6, "0123456789");
    }
}
