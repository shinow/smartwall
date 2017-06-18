/**
 * 天恒众航(2013)
 */
/**
 * 天恒众航(2013)
 */
package link.smartwall.filter;

import javax.xml.bind.annotation.XmlEnum;

import link.smartwall.base.api.IdName;

/**
 * 过滤器类型
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-4-10 lexloo
 *        </pre>
 */
@XmlEnum
public enum FilterItemComp {
    /**
     * 文本框
     */
    TEXTFIELD,
    /**
     * 树
     */
    TREE,
    /**
     * 自动完成("客户端")
     */
    AUTOCOMPLETE,
    /**
     * 自动完成("服务器")
     */
    AUTOCOMPLETE2,
    /**
     * 下拉选择
     */
    COMBOBOX,
    /**
     * 多选
     */
    MULTISELECT,
    /**
     * 日期选择
     */
    DATE,
    /**
     * 日期时间选择
     */
    DATETIMEPICKER,
    /**
     * 弹出单选
     */
    POPSELECT,
    /**
     * 年月选择
     */
    YEAR_MONTH;
    /**
     * 用于下拉列表填充
     */
    private static IdName[] idNames = {new IdName(TEXTFIELD, "文本框"),
                                       new IdName(TREE, "树"),
                                       new IdName(AUTOCOMPLETE, "自动完成(客户端)"),
                                       new IdName(AUTOCOMPLETE2, "自动完成(服务器)"),
                                       new IdName(COMBOBOX, "下拉选择"),
                                       new IdName(MULTISELECT, "下拉多选"),
                                       new IdName(DATE, "日期选择"),
                                       new IdName(DATETIMEPICKER, "日期时间选择"),
                                       new IdName(POPSELECT, "弹出单选"),
                                       new IdName(YEAR_MONTH, "年月选择")};

    /**
     * 
     * @return idNames, 用于下拉列表
     */
    public static IdName[] getIdNames() {
        return idNames;
    }
}
