/**
 * SmartWall(2015)
 */
package link.smartwall.grid.entity;

import link.smartwall.base.api.IdName;
import link.smartwall.util.DateUtils;

/**
 * 默认值设置器
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤助手 4.0.0
 * 
 *        <pre>
 * 历史：
 *      2015年11月29日 lexloo * 建立
 * </pre>
 */
public final class DefaultValueSettor {
    /**
     * 构造函数
     */
    private DefaultValueSettor() {
        // 构造函数
    }

    /**
     * 返回支持的默认值列表
     * 
     * @return 默认值列表
     */
    public static IdName[] getDefaultValueIdNames() {
        return new IdName[]{new IdName("", "无"),
                            new IdName("$TODAY", "当天(日期控件)"),
                            new IdName("$FIRST_OF_MONTH", "本月第一天(日期控件)"),
                            new IdName("$COMBOBOX_DEF", "下拉默认值(下拉选择)"),
                            new IdName("$COMBOBOX_DEPEND", "下拉联动(下拉选择)(父组件ID)")};
    }

    /**
     * 获取默认值
     * 
     * @param code 默认值代码
     * @return 默认值
     */
    public static String getDefaultValue(String code) {
        switch (code) {
        case "$TODAY":
            return DateUtils.formatCurrentDate();
        case "$FIRST_OF_MONTH":
            return DateUtils.formatFirstDayOfCurrentMonth();
        default:
            return null;
        }
    }
}
