package link.smartwall.base.entity;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Chain;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

import link.smartwall.basemodel.AbstractObject;
import link.smartwall.util.StrUtils;

import java.util.Date;

/**
 * 租户表entity
 *
 * @author yty
 */
@Table("SFA_TENANT")
public class Tenant extends AbstractObject {
    @Id(auto = false)
    @Column
    @Prev(@SQL("select TANENT_SEQUENCE.nextval from dual"))
    private Integer id;
    @Column
    private String code;
    @Column
    private String caption;
    @Column
    private Date start_date;
    @Column
    private Date end_date;
    @Column
    private String contact;
    @Column
    private String contact_phone;
    @Column
    private String remark;
    @Column
    private String guid;
    @Column
    private String provinceid;
    @Column
    private String cityid;
    @Column
    private String tenant_trade;
    @Column
    private String is_pay;
    @Column
    private String tenant_source;
    @Column
    private String web_url;
    @Column
    private String account;
    @Column
    private String pwd;
    @Column
    private String open_quota;
    @Column
    private String pay_count;
    @Column
    private String bound_count;
    @Column
    private String emp_count;
    @Column
    private String app_url;
    @Column
    private String address;
    @Column
    private String qq;
    @Column
    private String email;
    @Column
    private String industry_guid;
    @Column
    private String province;
    @Column
    private String city;

    /**
     * 仅用于页面展示汉字用，不做数据库存储
     * BZHFIVE-959
     */
    private String payChinese;
    
    
    /**
     * @return province
     */
    
    public String getProvince() {
        return province;
    }



    
    /** 
     * @param province 要设置的 province 
     */
    
    public void setProvince(String province) {
        this.province = province;
    }



    
    /**
     * @return city
     */
    
    public String getCity() {
        return city;
    }



    
    /** 
     * @param city 要设置的 city 
     */
    
    public void setCity(String city) {
        this.city = city;
    }



    /**
     * @return industry_guid
     */
    
    public String getIndustry_guid() {
        return industry_guid;
    }


    
    /** 
     * @param industry_guid 要设置的 industry_guid 
     */
    
    public void setIndustry_guid(String industry_guid) {
        this.industry_guid = industry_guid;
    }


    /**
     * @return tenant_trade
     */

    public String getTenant_trade() {
        return tenant_trade;
    }


    /**
     * @param tenant_trade 要设置的 tenant_trade
     */

    public void setTenant_trade(String tenant_trade) {
        this.tenant_trade = tenant_trade;
    }


    /**
     * @return is_pay
     */

    public String getIs_pay() {
        return is_pay;
    }


    /**
     * @param is_pay 要设置的 is_pay
     */

    public void setIs_pay(String is_pay) {
        this.is_pay = is_pay;
    }


    /**
     * @return tenant_source
     */

    public String getTenant_source() {
        return tenant_source;
    }


    /**
     * @param tenant_source 要设置的 tenant_source
     */

    public void setTenant_source(String tenant_source) {
        this.tenant_source = tenant_source;
    }


    /**
     * @return web_url
     */

    public String getWeb_url() {
        return web_url;
    }


    /**
     * @param web_url 要设置的 web_url
     */

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }


    /**
     * @return account
     */

    public String getAccount() {
        return account;
    }


    /**
     * @param account 要设置的 account
     */

    public void setAccount(String account) {
        this.account = account;
    }


    /**
     * @return pwd
     */

    public String getPwd() {
        return pwd;
    }


    /**
     * @param pwd 要设置的 pwd
     */

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


    /**
     * @return open_quota
     */

    public String getOpen_quota() {
        return open_quota;
    }


    /**
     * @param open_quota 要设置的 open_quota
     */

    public void setOpen_quota(String open_quota) {
        this.open_quota = open_quota;
    }


    /**
     * @return pay_count
     */

    public String getPay_count() {
        return pay_count;
    }


    /**
     * @param pay_count 要设置的 pay_count
     */

    public void setPay_count(String pay_count) {
        this.pay_count = pay_count;
    }


    /**
     * @return bound_count
     */

    public String getBound_count() {
        return bound_count;
    }


    /**
     * @param bound_count 要设置的 bound_count
     */

    public void setBound_count(String bound_count) {
        this.bound_count = bound_count;
    }


    /**
     * @return emp_count
     */

    public String getEmp_count() {
        return emp_count;
    }


    /**
     * @param emp_count 要设置的 emp_count
     */

    public void setEmp_count(String emp_count) {
        this.emp_count = emp_count;
    }


    /**
     * @return app_url
     */

    public String getApp_url() {
        return app_url;
    }


    /**
     * @param app_url 要设置的 app_url
     */

    public void setApp_url(String app_url) {
        this.app_url = app_url;
    }

    /**
     * @return address
     */

    public String getAddress() {
        return address;
    }


    /**
     * @param address 要设置的 address
     */

    public void setAddress(String address) {
        this.address = address;
    }


    /**
     * @return qq
     */

    public String getQq() {
        return qq;
    }


    /**
     * @param qq 要设置的 qq
     */

    public void setQq(String qq) {
        this.qq = qq;
    }


    /**
     * @return email
     */

    public String getEmail() {
        return email;
    }


    /**
     * @param email 要设置的 email
     */

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    /**
     * 仅用于页面展示汉字用
     *
     * @return
     */
    public String getPayChinese() {
        return (StringUtils.isNotBlank(is_pay) && is_pay.equals("1")) ? "是" : "否";
    }

    public void setPayChinese(String payChinese) {
        this.payChinese = payChinese;
    }

    @Override
    public void afterInsert(Dao dao) {
        dao.insert(
                "sfa_dept",
                Chain.make("guid", StrUtils.uuid())
                        .add("code", "001").add("name", "默认部门")
                        .add("tenant_id", getId())
                        .add("data_time", new Date())
                        .add("data_is_deleted", 0)
                        .add("modify_flag", 1)
                        .add("modify_time", new Date()));

        dao.insert(
                "sfa_role",
                Chain.make("guid", StrUtils.uuid())
                        .add("code", getCode() + "_sys")
                        .add("name", "系统管理员")
                        .add("tenant_id", getId())
                        .add("data_time", new Date())
                        .add("data_is_deleted", 0)
                        .add("modify_flag", 1)
                        .add("modify_time", new Date())
                        .add("is_system", 1));
        dao.insert(
                "sfa_role",
                Chain.make("guid", StrUtils.uuid())
                        .add("code", getCode() + "_mobi")
                        .add("name", "销售代表")
                        .add("tenant_id", getId())
                        .add("data_time", new Date())
                        .add("data_is_deleted", 0)
                        .add("modify_flag", 1)
                        .add("modify_time", new Date())
                        .add("is_mobi_default", 1));
        dao.insert(
                "sfa_timecard",
                Chain.make("guid", StrUtils.uuid())
                        .add("kq_name", "默认考勤").add("kq_type", 1)
                        .add("tenant_id", getId())
                        .add("data_time", new Date())
                        .add("data_is_deleted", 0)
                        .add("modify_flag", 1)
                        .add("modify_time", new Date()));
    }
}
