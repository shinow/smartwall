/**
 * SmartWall(2013)
 */
package link.smartwall.base.api;


/**
 * 列表存储对象
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-7-9 lexloo
 * </pre>
 */
public class IdName {
    /**
     * Id
     */
    private Object id;
    /**
     * Name
     */
    private String name;
    /**
     * 附加对象
     */
    private Object extra;

    /**
     * 构造函数
     */
    public IdName() {

    }

    /**
     * 构造函数
     * 
     * @param id id
     * @param name name
     */
    public IdName(Object id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 构造函数
     * 
     * @param id Id
     * @param name 名称
     * @param extra 附加对象
     */
    public IdName(Object id, String name, Object extra) {
        this(id, name);

        this.setExtra(extra);
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * @return the id
     */
    public Object getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Object id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the extra
     */
    public Object getExtra() {
        return extra;
    }

    /**
     * @param extra the extra to set
     */
    public void setExtra(Object extra) {
        this.extra = extra;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((extra == null) ? 0 : extra.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        IdName other = (IdName) obj;
        if (extra == null) {
            if (other.extra != null) {
                return false;
            }
        } else if (!extra.equals(other.extra)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
