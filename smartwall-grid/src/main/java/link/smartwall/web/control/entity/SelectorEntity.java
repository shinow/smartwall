/**
 * 天恒众航(2013) */
/**
 * 天恒众航(2013) */
package link.smartwall.web.control.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

/**
 * 组件配置实体
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-7-3 lexloo
 * </pre>
 */
@Table("cfg_ctl_selector")
public class SelectorEntity {
    /**
     * 主键Id
     */
    @Column
    @Id(auto = false)
    @Prev(@SQL("select cfg_ctl_selector_s.nextval from dual"))
    private int id;
    /**
     * 代码
     */
    @Column
    @Name
    private String code;
    /**
     * 标题
     */
    @Column
    private String title;
    /**
     * 配置
     */
    @Column
    private String conf;
    /**
     * 备注
     */
    @Column
    private String remark;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setName(String code) {
        this.code = code;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the conf
     */
    public String getConf() {
        return conf;
    }

    /**
     * @param conf the conf to set
     */
    public void setConf(String conf) {
        this.conf = conf;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
