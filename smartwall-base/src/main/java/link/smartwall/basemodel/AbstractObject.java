/**
 * 天恒众航(2013)
 */
package link.smartwall.basemodel;

import org.nutz.dao.Dao;
import org.nutz.dao.entity.annotation.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import link.smartwall.base.http.UserManager;
import link.smartwall.base.http.WebUser;
import link.smartwall.util.NumberUtils;
import link.smartwall.util.StrUtils;
import link.smartwall.util.reflect.ReflectUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础模型对象
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-1-9 lexloo
 *        </pre>
 */
public abstract class AbstractObject implements IOprEvent, Cloneable {
    /**
     * 日志记录器
     */
    private static final Logger LOG = LoggerFactory.getLogger(AbstractObject.class.getName());
    /**
     * 最大日志串长度
     */
    private static final int MAX_LOG_STR_LENGTH = 255;

    /**
     * 字段列表
     */
    private transient String[] fieldArray;

    /**
     * 构造函数
     */
    public AbstractObject() {}

    /**
     * 具体由子类来实现。
     *
     * @return 实体的业务描述。当前主要用于“我的待办”和“我的已办”列表中的工作项的展示。
     */
    public String getDescription() {
        return "";
    }

    /**
     * 设置字段的值
     * 
     * @param field 字段
     * @param value 值
     */
    public void setValue(String field, Object value) {
        ReflectUtils.setValue(this, field, value);
    }

    /**
     * 获取字段的值
     * 
     * @param field 字段名
     * @return 字段的值
     */
    public Object getValue(String field) {
        return ReflectUtils.getValue(this, field);
    }

    /**
     * 获取Id值
     * 
     * @return Id值
     */
    public final long entityIdValue() {
        long rtn = 0L;

        String id = this.entityIdField();
        if (StrUtils.isEmpty(id)) {
            LOG.error("主键字段为空,不能获取主键值");
            rtn = 0L;
        } else {
            rtn = NumberUtils.c2long(this.getValue(id), 0L);
        }

        return rtn;
    }

    /**
     * 获取实体对应表名,子类继承实现
     * 
     * @return 表名
     */
    public String entityTableName() {
        return "";
    }

    /**
     * 获取Id字段,子类继承实现
     * 
     * @return Id字段名
     */
    public String entityIdField() {
        return "";
    }

    /**
     * 获取属性对应的数据库字段
     * 
     * @param field 对象属性
     * @return 数据库字段
     * @since 外勤管家 4.0
     */
    public final String entityDbField(String field) {
        try {
            Field f = this.getClass().getDeclaredField(field);

            Column column = f.getAnnotation(Column.class);
            if (column != null) {
                return column.value().length() == 0 ? field : column.value();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException("获取业务模型字段对应的数据库字段出错");
    }

    /**
     * 获取到所有属性,设计器使用
     * 
     * @return 所有属性
     */
    public synchronized String[] configField() {
        if (fieldArray == null) {
            List<String> fieldList = new ArrayList<String>();
            // 来一个空，允许某个选项值为空白
            fieldList.add("");

            for (Field f : this.getClass().getDeclaredFields()) {
                fieldList.add(f.getName());
            }

            fieldArray = fieldList.toArray(new String[0]);
        }

        return fieldArray;
    }

    /**
     * 是否进行DB持久化，默认为true，针对某些对象，是进行文件方式的存储，不进行数据库持久化，如：定时定位数据。
     * 
     * @return 是否进行DB持久化
     */
    public boolean isDBPersistent() {
        return true;
    }

    /**
     * 更新记录更改情况
     * 
     * @param changedValues 更改记录
     * @param dao dao
     */
    protected void updateChangedLog(DataChangeLogList changedValues, Dao dao) {
        if (changedValues.isEmpty()) {
            return;
        }

        // 现在只能处理后台数据修改并记录修改人
        DataChangeLogInfo logInfo = new DataChangeLogInfo();
        logInfo.setTable(this.entityTableName());

        WebUser wu = UserManager.getCurrentUser();
//        logInfo.setUserId(wu.getId());
        logInfo.setUserName(wu.getCode());
        logInfo.setTenantId(wu.getTenantId());

        String logStr = changedValues.toLogInfo();
        logInfo.setLogInfo(logStr.substring(0, Math.min(MAX_LOG_STR_LENGTH, logStr.length())));

        dao.insert(logInfo);
    }

    @Override
    public AbstractObject clone() {
        AbstractObject ao = null;
        try {
            ao = (AbstractObject) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return ao;
    }

    @Override
    public void beforeInsert(Dao dao) {
    }

    @Override
    public void afterInsert(Dao dao) {}

    @Override
    public void beforeUpdate(Dao dao, AbstractObject prevObj) {}

    @Override
    public void afterUpdate(Dao dao, AbstractObject prevObj) {}

    @Override
    public void beforeDelete(Dao dao) {}

    @Override
    public void afterDelete(Dao dao) {}

    /**
     * 初始化部分值
     */
    public  void initValues(){};
}
