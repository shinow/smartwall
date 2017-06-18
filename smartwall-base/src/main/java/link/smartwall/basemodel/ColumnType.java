/**
 * SmartWall(2013)
 */
package link.smartwall.basemodel;

import link.smartwall.base.api.IdName;
import link.smartwall.util.NumberUtils;
import link.smartwall.util.StrUtils;

/**
 * 列类型
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-1-9 lexloo
 * </pre>
 */
public enum ColumnType {
    /**
     * 整数型
     */
    INT("int") {
        @Override
        public Object getDefValue(String defValue) {
            return NumberUtils.c2int(defValue, 0);
        }
    },
    /**
     * 长整型
     */
    LONG("long") {
        @Override
        public Object getDefValue(String defValue) {
            return NumberUtils.c2long(defValue, 0L);
        }
    },
    /**
     * 浮点型
     */
    DOUBLE("double") {
        @Override
        public Object getDefValue(String defValue) {
            return NumberUtils.c2double(defValue, 0D);
        }
    },
    /**
     * 字符串
     */
    STRING("String") {
        @Override
        public Object getDefValue(String defValue) {
            return StrUtils.c2str(defValue, "");
        }
    },
    /**
     * 日期型
     */
    DATE("Date") {
        @Override
        public Object getDefValue(String defValue) {
            throw new UnsupportedOperationException("还不支持的默认值设置");
        }
    },
    /**
     * 时间戳
     */
    TIMESTAMP("Timestamp") {
        @Override
        public Object getDefValue(String defValue) {
            throw new UnsupportedOperationException("还不支持的默认值设置");
        }
    },
    /**
     * 长文本
     */
    CLOB("Clob") {
        @Override
        public Object getDefValue(String defValue) {
            throw new UnsupportedOperationException("还不支持的默认值设置");
        }
    };

    // TODO 增加其它类型
    /**
     * 对应的Java类型
     */
    private String javaType;

    /**
     * 构造函数
     * 
     * @param javaType 对已的java类型
     */
    ColumnType(String javaType) {
        this.javaType = javaType;
    }

    /**
     * @return Java字段类型
     */
    public String getJavaType() {
        return this.javaType;
    }

    /**
     * 用于下拉列表填充
     */
    private static IdName[] idNames = {new IdName(INT, "整型"),
                                       new IdName(LONG, "长整型"),
                                       new IdName(DOUBLE, "浮点型"),
                                       new IdName(STRING, "字符串"),
                                       new IdName(DATE, "日期型"),
                                       new IdName(TIMESTAMP, "时间戳"),
                                       new IdName(CLOB, "长文本")};

    /**
     * 
     * @return idNames, 用于下拉列表
     */
    public static IdName[] getIdNames() {
        return idNames;
    }

    /**
     * 根据默认字符串生成默认值
     * 
     * @param defValue 默认字符串
     * @return 默认值
     */
    public abstract Object getDefValue(String defValue);
}
