package link.smartwall.kygj.questionbank.domain;

import org.xutils.http.annotation.HttpResponse;

import link.smartwall.kygj.questionbank.http.JsonResponseParser;

/**
 * 考试分类
 */
@HttpResponse(parser = JsonResponseParser.class)
public class Category {
    private String guid;
    private String kindGuid;
    private String name;

    public Category() {
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }


    public String getKindGuid() {
        return kindGuid;
    }

    public void setKindGuid(String kindGuid) {
        this.kindGuid = kindGuid;
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
