package link.smartwall.kygj.questionbank.adapter;

/**
 * 基础对象
 */

public abstract class BaseItem {
    private String guid;
    private String name;

    /**
     * 是否展开
     */
    private boolean expand;

    /**
     * 类型
     */
    public abstract int getType();

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }
}
