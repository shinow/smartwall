package link.smartwall.base.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

import link.smartwall.basemodel.BaseEntity;

/**
 * 行业
 *
 * @author <a herf="wangbo@infinitek.net">wangbo</a>
 * @version 1.0
 * @since 2017-05-03，10:44
 */
@Table("sfa_em_industry")
public class Industry extends BaseEntity {

    @Column
    private String code;

    @Column
    private String name;

    @Column
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
