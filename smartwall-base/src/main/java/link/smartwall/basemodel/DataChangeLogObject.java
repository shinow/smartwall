/**
 * NextVisual
 */
package link.smartwall.basemodel;

/**
 * 数据改变记录对象
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2014-7-8 lexloo
 * </pre>
 */
public class DataChangeLogObject {
    /**
     * 字段名称
     */
    private String name;
    /**
     * 原始值
     */
    private Object oldValue;
    /**
     * 新值
     */
    private Object newValue;
    /**
     * 是否改变
     */
    private boolean isChanged;

    /**
     * 数据改变对象
     * 
     * @param name 字段名称
     * @param oldValue 原来值
     * @param newValue 新值
     */
    public DataChangeLogObject(String name, Object oldValue, Object newValue) {
        this.name = name;
        this.oldValue = oldValue;
        this.newValue = newValue;

        this.setChanged(this.testChanged(oldValue, newValue));
    }

    /**
     * @param oldValue 旧值
     * @param newValue 新值
     * @return 不相同则为false
     */
    private boolean testChanged(Object oldValue, Object newValue) {
        if (oldValue == null) {
            return newValue != null;
        } else {
            return !oldValue.equals(newValue);
        }
    }

    @Override
    public String toString() {
        return name + ":" + oldValue + "->" + newValue;
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
     * @return the oldValue
     */
    public Object getOldValue() {
        return oldValue;
    }

    /**
     * @param oldValue the oldValue to set
     */
    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * @return the newValue
     */
    public Object getNewValue() {
        return newValue;
    }

    /**
     * @param newValue the newValue to set
     */
    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }

    /**
     * @return the isChanged
     */
    public boolean isChanged() {
        return isChanged;
    }

    /**
     * @param isChanged the isChanged to set
     */
    public void setChanged(boolean isChanged) {
        this.isChanged = isChanged;
    }
}
