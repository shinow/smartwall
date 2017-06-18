package link.smartwall.grid.entity;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.entity.annotation.View;

import link.smartwall.basemodel.BaseEntity;

@Table("conf_grid_action")
@View("vs_conf_grid_action")
public class ConfGridAction extends BaseEntity {
    @Column("master_guid")
    private String masterGuid;
    @Column("caption")
    private String caption;
    @Column("action")
    private String action;
    @Column("func_guid")
    private String funcGuid;
    @Column("show_index")
    private int showIndex;
    @Column("menu_button")
    private String menuButton;
    @Column("icon")
    private String icon;

    public String getMasterGuid() {
        return masterGuid;
    }

    public void setMasterGuid(String masterGuid) {
        this.masterGuid = masterGuid;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFuncGuid() {
        return funcGuid;
    }

    public void setFuncGuid(String funcGuid) {
        this.funcGuid = funcGuid;
    }

    public int getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(int showIndex) {
        this.showIndex = showIndex;
    }

    public String getMenuButton() {
        return menuButton;
    }

    public void setMenuButton(String menuButton) {
        this.menuButton = menuButton;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
