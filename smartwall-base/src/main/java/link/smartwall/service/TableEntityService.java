/**
 * SmartWall(2016)
 */
package link.smartwall.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import link.smartwall.base.api.Result;
import link.smartwall.basemodel.AbstractGuidObject;
import link.smartwall.basemodel.AbstractObject;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import java.util.*;

/**
 * 表实体服务,只支持增、删、改,直接对表进行操作
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤助手 5.0.0
 * 
 *        <pre>
 * 历史：
 *      2016年10月03日 lexloo * 建立
 *        </pre>
 */
@IocBean(args = {"refer:dao"})
public class TableEntityService extends BaseService {
    /**
     * 插入
     */
    public static final int MODIFY_FLAG_INSERT = 1;
    /**
     * 更新
     */
    public static final int MODIFY_FLAG_UPDATE = 2;
    /**
     * 删除
     */
    public static final int MODIFY_FLAG_DELETE = 3;
    /**
     * 改变标记字段，增量更新信息都需要
     */
    public static final String MODIFY_FLAG_FIELD = "modify_flag";
    /**
     * 改变时间字段，增量更新信息都需要
     */
    public static final String MODIFY_TIME_FIELD = "modify_time";
    /**
     * 数据是否已经删除
     */
    public static final String DATA_IS_DELETED = "data_is_deleted";
    /**
     * 数据创建时间
     */
    public static final String DATA_TIME = "data_time";

    /**
     * 构造函数
     */
    public TableEntityService(Dao dao) {
        super(dao);
    }

    /**
     * 插入表行
     * 
     * @param tableName 表名
     * @param tenantId 企业Id
     * @param data 数据
     * @return 操作结果
     */
    public Result insertTableRow(String tableName, int tenantId, Map<String, Object> data) {
        try {
            Map<String, Object> ds = new HashMap<>();
            ds.put(".table", tableName);
            ds.put("tenant_id", tenantId);
            ds.put(MODIFY_FLAG_FIELD, MODIFY_FLAG_INSERT);
            ds.put(MODIFY_TIME_FIELD, new Date());
            ds.put(DATA_IS_DELETED, 0);
            ds.put(DATA_TIME, new Date());
            String guid = (String) data.get("guid");
            if (StringUtils.isBlank(guid)) {
                data.put("guid", UUID.randomUUID().toString().replaceAll("-", ""));
            }
            ds.putAll(data);
            Object abstractObject = this.dao().insert(ds);
            JSONObject r = new JSONObject();
            r.put("guid", data.get("guid"));
            if (abstractObject instanceof AbstractGuidObject) {
                r.put("bizDescription", ((AbstractObject) abstractObject).getDescription());
            }
            return Result.successResult(r);
        } catch (Exception ex) {
            return Result.failureResult("insert table row error.");
        }
    }

    /**
     * 批量插入表行
     * 
     *
     * @param tableName 表名
     * @param empGuid
     * @param tenantId 企业id
     * @param data 数据
     * @return 操作结果
     */
    public Result insertMultiTableRow(String tableName, String empGuid, int tenantId, JSONArray data) {
        try {
            List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
            for (Object map : data) {
                Map<String, Object> ds = new HashMap<>();
                ds.put(".table", tableName);
                ds.put("tenant_id", tenantId);
                ds.put("data_time", new Date());
                ds.put(MODIFY_FLAG_FIELD, MODIFY_FLAG_INSERT);
                ds.put(MODIFY_TIME_FIELD, new Date());
                ds.put(DATA_IS_DELETED, 0);
                ds.putAll((JSONObject) map);
                dataList.add(ds);
            }
            dao().fastInsert(dataList);
            return Result.successResult("success");
        } catch (Exception ex) {
            // TODO
            return Result.failureResult("insert table row error.");
        }
    }

    /**
     * 更新表行
     * 
     * @param tableName 表名
     * @param guid GUID
     * @param data 数据
     * @return 操作结果
     */
    public Result updateTableRow(String tableName, String guid, Map<String, Object> data) {
        Map<String, Object> ds = new HashMap<>();
        ds.put(".table", tableName);
        ds.put("*guid", guid);
        ds.put("guid", guid);
        ds.put(MODIFY_FLAG_FIELD, MODIFY_FLAG_UPDATE);
        ds.put(MODIFY_TIME_FIELD, new Date());
        ds.put(DATA_IS_DELETED, 0);

        ds.putAll(data);

        this.dao().update(ds);

        return Result.successResult();
    }

    /**
     * 删除表行
     * 
     * @param tableName 表名
     * @param guid GUID
     * @return 操作结果
     */
    public Result deleteTableRow(String tableName, String guid) {
        Map<String, Object> ds = new HashMap<>();

        ds.put(".table", tableName);
        ds.put("*guid", guid);
        ds.put("guid", guid);
        ds.put(MODIFY_FLAG_FIELD, MODIFY_FLAG_DELETE);
        ds.put(MODIFY_TIME_FIELD, new Date());
        ds.put(DATA_IS_DELETED, 1);

        this.dao().update(ds);

        return Result.successResult();
    }

}
