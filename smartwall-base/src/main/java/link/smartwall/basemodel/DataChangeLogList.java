/**
 * NextVisual
 */
package link.smartwall.basemodel;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据改变保存列表,只能保存DataChangeLogObject对象,并且值保留isChanged对象
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2014-7-9 lexloo
 * </pre>
 */
public class DataChangeLogList {
    /**
     * 存储容器
     */
    private List<DataChangeLogObject> collection = new ArrayList<DataChangeLogObject>();

    /**
     * 增加数据对象
     * 
     * @param obj 对象
     */
    public void add(DataChangeLogObject obj) {
        if (obj.isChanged()) {
            collection.add(obj);
        }
    }

    /**
     * 是否为空
     * 
     * @return 是否为空
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * @return 获取Log信息
     */
    public String toLogInfo() {
        StringBuilder sb = new StringBuilder();

        for (DataChangeLogObject o : collection) {
            sb.append(o).append(";");
        }

        return sb.toString();
    }
}
