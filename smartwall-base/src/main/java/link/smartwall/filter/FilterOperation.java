/**
 * SmartWall(2013)
 */
package link.smartwall.filter;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlEnum;

import link.smartwall.base.api.IdName;

/**
 * 过滤操作类型
 * 
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @version 1.0
 * @since  2.0
 *        <p/>
 * 
 *        <pre>
 * 历史：
 *     建立: 2013-3-18 lexloo
 * </pre>
 */
@XmlEnum
public enum FilterOperation {

    /**
     * select id from auth_dept where id in (1,2,3)
     */
    IN {
        @Override
        public String getValue(Object value) {
            // TODO 觉得这里有问题
            @SuppressWarnings("unchecked")
            List<Map<String, String>> result = (List<Map<String, String>>) value;
            StringBuffer sb = new StringBuffer();
            for (Map<String, String> map : result) {
                sb.append(map.values().iterator().next());
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }

        @Override
        public String getOprStr() {
            return "in";
        }
    },

    /**
     * 包含
     */
    LIKE {
        @Override
        public String getValue(Object value) {
            return "%" + value + "%";
        }

        @Override
        public String getOprStr() {
            return "like";
        }
    },
    /**
     * 等于
     */
    EQUAL {
        @Override
        public String getOprStr() {
            return "=";
        }
    },
    /**
     * 大于
     */
    GT {
        @Override
        public String getOprStr() {
            return ">";
        }
    },
    /**
     * 大于等于
     */
    EOGT {
        @Override
        public String getOprStr() {
            return ">=";
        }
    },
    /**
     * 小于
     */
    LT {
        @Override
        public String getOprStr() {
            return "<";
        }
    },
    /**
     * 小于等于
     */
    EOLT {
        @Override
        public String getOprStr() {
            return "<=";
        }
    },
    /**
     * 包含下级
     */
    INCLUDE {
        @Override
        public String getOprStr() {
            return "";
        }
    };

    /**
     * 获取对象的值，对于某些操作，如like，需要返回'%值%';
     * 
     * @param value 值
     * @return 处理后的值
     */
    public String getValue(Object value) {
        return value.toString();
    }

    /**
     * 获取操作串
     * 
     * @return 操作串
     */
    public abstract String getOprStr();

    /**
     * 用于下拉列表填充
     */
    private static IdName[] idNames = {new IdName(LIKE, "包含"),
                                       new IdName(IN, "存在"),
                                       new IdName(EQUAL, "等于"),
                                       new IdName(GT, "大于"),
                                       new IdName(EOGT, "大于等于"),
                                       new IdName(LT, "小于"),
                                       new IdName(EOLT, "小于等于"),
                                       new IdName(INCLUDE, "包含下级")};

    /**
     * 
     * @return idNames, 用于下拉列表
     */
    public static IdName[] getIdNames() {
        return idNames;
    }
}
