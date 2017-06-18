/**
 * 天恒众航(2013)
 */
package link.smartwall.basemodel;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;

import link.smartwall.util.StrUtils;

/**
 * 基础模型对象
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-1-9 lexloo
 *        </pre>
 */
public abstract class AbstractGuidObject extends AbstractObject {
    /**
     * Guid
     */
    @Column
    @Name
    private String guid;

    /**
     * @return the guid
     */
    public String getGuid() {
        return guid;
    }

    /**
     * @param guid the guid to set
     */
    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public void initValues() {
        if (StringUtils.isBlank(guid)) {
            this.guid = StrUtils.uuid();
        }
    }
}
