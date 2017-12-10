package link.smartwall.kygj.questionbank.domain;

/**
 * 考试种类
 */
public class Kind {
    private String guid;
    private String name;

    public Kind(String guid, String name) {
        this.guid = guid;
        this.name = name;
    }

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

    @Override
    public String toString() {
        return this.name;
    }
}
