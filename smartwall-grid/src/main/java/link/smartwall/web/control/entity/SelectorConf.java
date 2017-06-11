/**
 * 天恒众航(2013) */
/**
 * 天恒众航(2013) */
package link.smartwall.web.control.entity;

import com.itfsm.util.StrUtils;

/**
 * Selector组件配置对象
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
public class SelectorConf {
    /**
     * 版本
     */
    private String version = "1.0";
    /**
     * 未选标题
     */
    private String titleUnSel;
    /**
     * 选择标题
     */
    private String titleSel;
    /**
     * 数据源
     */
    private String datasource;
    /**
     * 使用参数表
     */
    private String parameters;
    /**
     * 过滤名
     */
    private String filterName;

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the titleUnSel
     */
    public String getTitleUnSel() {
        return titleUnSel;
    }

    /**
     * @param titleUnSel the titleUnSel to set
     */
    public void setTitleUnSel(String titleUnSel) {
        this.titleUnSel = titleUnSel;
    }

    /**
     * @return the titleSel
     */
    public String getTitleSel() {
        return titleSel;
    }

    /**
     * @param titleSel the titleSel to set
     */
    public void setTitleSel(String titleSel) {
        this.titleSel = titleSel;
    }

    /**
     * @return the datasource
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * @param datasource the datasource to set
     */
    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    /**
     * @return the filterName
     */
    public String getFilterName() {
        return filterName;
    }

    /**
     * @param filterName the filterName to set
     */
    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    /**
     * @return the parameters
     */
    public String getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    /**
     * 获取参数Array
     * 
     * @return 参数Array
     */
    public String[] getParameterArray() {
        if (StrUtils.isEmpty(this.parameters)) {
            return new String[0];
        } else {
            return this.parameters.replaceAll("\\W", "").toLowerCase().split(",");
        }
    }
}
