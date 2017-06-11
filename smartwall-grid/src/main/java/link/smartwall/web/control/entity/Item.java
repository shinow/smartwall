/**
 * 天恒众航(2013) */
/**
 * 天恒众航(2013) */
package link.smartwall.web.control.entity;

/**
 * 组件显示项
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
public class Item {
    /**
     * Id
     */
    private int id;
    /**
     * Caption
     */
    private String caption;

    /**
     * 构造函数
     * 
     * @param id Id
     * @param caption 标题
     */
    public Item(int id, String caption) {
        this.id = id;
        this.caption = caption;
    }

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
     * @return the caption
     */
    public String getCaption() {
        return caption;
    }

    /**
     * @param caption the caption to set
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }
}
