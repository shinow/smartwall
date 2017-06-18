/**
 * 天恒众航（北京）科技股份公司(2015)
 */
package link.smartwall.service;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import link.smartwall.base.api.Result;
import link.smartwall.base.http.UserManager;
import link.smartwall.basemodel.AbstractObject;
import link.smartwall.basemodel.ObjectFactory;
import link.smartwall.util.DateUtils;
import link.smartwall.util.reflect.ReflectUtils;

/**
 * 抽象服务
 *
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @version 1.0
 * @since 外勤助手 4.0.0
 *        <p>
 * 
 *        <pre>
 * 历史：
 *      2015年2月27日 lexloo * 建立
 *        </pre>
 */
@IocBean(args = { "refer:dao" })
public class CommonObjectService extends BaseService {
	/**
	 * 构造函数
	 */
	public CommonObjectService(Dao dao) {
		super(dao);
	}

	public Result insert(String code, final Map<String, Object> values, final Boolean isExtend) {
		final AbstractObject ao = ObjectFactory.INSTANCE.getObjectByCode(code);
		if (null != ao) {
			for (String key : values.keySet()) {
				ReflectUtils.setValue(ao, key, values.get(key));
			}
			Integer tenantId = 0;
			if (null != UserManager.getCurrentUser()) {
				tenantId = UserManager.getCurrentUser().getTenantId();
			}

			ao.setValue("dataTime", DateUtils.formatDateTime(new Date()));
			ao.setValue("tenantid", tenantId);
			ao.setValue("dataIsDeleted", 0);
			ao.setValue("modifyFlag", 1);
			ao.setValue("modifyTime", DateUtils.formatDateTime(new Date()));

			Trans.exec(new Atom() {
				@Override
				public void run() {
					ao.beforeInsert(dao());
					dao().insert(ao, true, false, true);
					ao.afterInsert(dao());
					if (isExtend) {
						/**
						 * 判断是否有自定义字段，插入自定义字段数据表中 yty 2017.4.7
						 */
						Object extendFields = values.get("extendFields");
						if (null != extendFields) {
							JSONObject jo = JSONObject.parseObject(extendFields.toString());
							Sql sql = Sqls.create(
									"insert into sfa_fields_extend (main_guid,field,value) values(@mainGuid,@field,@value)");
							for (String key : jo.keySet()) {
								sql.setParam("mainGuid", ao.getValue("guid")).setParam("field", key).setParam("value",
										jo.getString(key));
								sql.addBatch();
							}
							dao().execute(sql);
						}

					}
				}
			});
			return Result.successResult();
		} else {
			throw new IllegalStateException("没有这个类!");
		}
	}

	public Result update(final Boolean isExtend, final String code, final Map<String, Object> values) {
		final AbstractObject ao = dao().fetch(ObjectFactory.INSTANCE.getObjectNoGuid(code).getClass(),
				values.get("guid").toString());
		if (null != ao) {
			for (String key : values.keySet()) {
				ReflectUtils.setValue(ao, key, values.get(key));
			}

			ao.setValue("modifyFlag", 2);
			ao.setValue("modifyTime", DateUtils.formatDateTime(new Date()));

			Trans.exec(new Atom() {
				@Override
				public void run() {
					ao.beforeUpdate(dao(), ao);
					dao().update(ao);
					ao.afterUpdate(dao(), ao);
					if (isExtend) {
						/**
						 * 判断是否有自定义字段，先删除以保存的自定义字段值，再重新插入自定义字段数据表中 yty 2017.4.7
						 */
						if (null != values.get("extendFields")) {
							JSONObject jo = JSONObject.parseObject(values.get("extendFields").toString());
							Sql sql = Sqls.create(
									"insert into sfa_fields_extend (main_guid,field,value) values(@mainGuid,@field,@value)");
							for (String key : jo.keySet()) {
								sql.setParam("mainGuid", ao.getValue("guid")).setParam("field", key).setParam("value",
										jo.getString(key));
								sql.addBatch();
							}
							Sql delSql = Sqls.create("delete from sfa_fields_extend where main_guid = @mainGuid");
							delSql.setParam("mainGuid", ao.getValue("guid"));
							dao().execute(delSql);
							dao().execute(sql);
						}
					}
				}
			});
		} else {
			return Result.failureResult("没有这个类");
		}
		return Result.successResult();
	}

	public Result deleteByGuid(String code, String guid) {
		final AbstractObject ao = ObjectFactory.INSTANCE.getObjectNoGuid(code);
		if (null != ao) {
			ao.setValue("guid", guid);
			ao.setValue("dataIsDeleted", 1);
			ao.setValue("modifyFlag", 3);
			ao.setValue("modifyTime", new Date());
			Trans.exec(new Atom() {
				@Override
				public void run() {
					ao.beforeDelete(dao());
					dao().updateIgnoreNull(ao);
					ao.afterDelete(dao());
				}
			});
			return Result.successResult();
		} else {
			return Result.failureResult("没有这个类");
		}
	}

	public Result queryByPager(String code, Map<String, Object> values, Integer pageNumber, int pageSize) {
		if (null == pageNumber || 0 == pageNumber) {
			pageNumber = 1;
		}
		Pager pager = dao().createPager(pageNumber, pageSize);
		Class<?> clazz = ObjectFactory.INSTANCE.getObjectClass(code);
		if (null != clazz) {
			Cnd cnd = Cnd.where("1", "=", 1);
			if (values != null && !values.isEmpty()) {
				for (String key : values.keySet()) {
					cnd.and(key, "=", values.get(key));
				}
			}
			List<?> list = dao().query(clazz, cnd, pager);
			pager.setRecordCount(dao().count(clazz));
			return Result.successResult(new QueryResult(list, pager));
		} else {
			return Result.failureResult("没有这个类");
		}
	}

	public Result query(String code, Map<String, Object> values) {

		Class<?> clazz = ObjectFactory.INSTANCE.getObjectClass(code);
		if (null != clazz) {
			Cnd cnd = Cnd.where("data_is_deleted", "=", 0);
			if (values != null && !values.isEmpty()) {
				for (String key : values.keySet()) {
					cnd.and(key, "=", values.get(key));
				}
			}
			List<?> list = dao().query(clazz, cnd);
			return Result.successResult(list);
		} else {
			return Result.failureResult("没有这个类");
		}

	}

	public Result getDataByGuid(Boolean isExtend, String code, String guid) {
		Class<?> clazz = ObjectFactory.INSTANCE.getObjectClass(code);
		Object obj = dao().fetch(clazz, guid);
		Map<String, Object> map = ConvertObjToMap(obj);
		/**
		 * 判断是否有自定义字段，查询该数据关联的自定义字段保存值 yty 2017.4.7
		 */
		if (isExtend) {
			List<Record> list = dao().query("sfa_fields_extend", Cnd.where("main_guid", "=", guid));
			for (Record r : list) {
				map.put(r.getString("field"), r.get("value"));
			}
		}
		return Result.successResult(map);

	}

	public Result queryByTable(String code, Map<String, Object> values) {
		Cnd cnd = Cnd.where("data_is_deleted", "=", 0);
		if (values != null && !values.isEmpty()) {
			for (String key : values.keySet()) {
				cnd.and(key, "=", values.get(key));
			}
		}
		List<Record> records = dao().query(code, cnd);
		return Result.successResult(records);
	}

	public Result batchDelByGuid(String tName, JSONArray guids) {
		Sql sql = Sqls
				.create("UPDATE $tName SET data_is_deleted=1,modify_flag = 3,modify_time=@modifyTime WHERE guid=@guid");
		sql.setVar("tName", tName);
		for (int i = 0; i < guids.size(); i++) {
			sql.setParam("modifyTime", new Date());
			sql.setParam("guid", guids.get(i));
			sql.addBatch();
		}
		dao().execute(sql);
		return Result.successResult();
	}

	public static Map<String, Object> ConvertObjToMap(Object obj) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null)
			return null;
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				try {
					Field f = obj.getClass().getDeclaredField(fields[i].getName());
					f.setAccessible(true);
					Object o = f.get(obj);
					reMap.put(fields[i].getName(), o);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reMap;
	}

}
