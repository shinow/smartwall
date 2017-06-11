package link.smartwall.mobiutils;
//package com.itfsm.mobiutils;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Map;
//
//import org.nutz.dao.Cnd;
//import org.nutz.dao.Dao;
//import org.nutz.dao.Sqls;
//import org.nutz.dao.sql.Sql;
//import org.nutz.dao.sql.SqlCallback;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.dcits.application.paramter.SysParameterM;
//import com.dcits.data.provider.entity.DropDownForMobile;
//import com.dcits.xsb.db.DbUtils;
//import com.itfsm.base.http.parameter.StandardParameters;
//import com.itfsm.base.util.StrUtils;
//import com.itfsm.grid.controller.RightsControlType;
//import com.itfsm.grid.controller.rights.IMobileRightsController;
//import com.itfsm.util.NumberUtils;
//
///**
// * cfg_m_dropdown SQL 帮助类
// * 
// * @version 1.0
// * @author <a href="ttan_style@sina.cn">ttan</a>
// * @since 销售宝 2.0
// * 
// *        <pre>
// * 历史：
// *      建立: 2014年6月25日  ttan
// * </pre>
// */
//public final class MobileDropDownSqlHelper {
//    /**
//     * 默认构造方法
//     */
//    private MobileDropDownSqlHelper() {
//        //
//    }
//
//    /**
//     * 获取SQL配置
//     * 
//     * @param dao 数据访问对象
//     * @param cfg SQL配置
//     * @param paramsMap 查询参数
//     * @return 查询值
//     */
//    public static Object getSQLOutput(Dao dao, final DropDownForMobile cfg, Map<String, Object> paramsMap) {
//        Sql sql = getQuerySql(dao, cfg, paramsMap);
//        sql.setCallback(new MobileDropDownCallback(cfg));
//        dao.execute(sql);
//
//        return sql.getResult();
//    }
//
//    /**
//     * 获取SQL查询对象
//     * 
//     * @param dao dao
//     * @param cfg 配置信息
//     * @param paramsMap 参数
//     * @return SQL查询对象
//     */
//    public static Sql getQuerySql(Dao dao, DropDownForMobile cfg, Map<String, Object> paramsMap) {
//        String sqlValue = cfg.getValue();
//
//        // 如果数据是产品权限控制
//        if (cfg.isProductControlAble()) {
//            // 检查该企业，是否配置产品权限
//            int tenantId = NumberUtils.c2int(paramsMap.get(StandardParameters.COMM_TENANT_ID));
//            SysParameterM sysParam =
//                    dao.fetch(SysParameterM.class,
//                              Cnd.where("tenantid", "=", tenantId).and("code", "=", "emp_product_control"));
//
//            if (sysParam != null && "true".equals(sysParam.getValue())) {
//                sqlValue =
//                        "select t1.* from ("
//                                + sqlValue
//                                + ") t1 inner join sys_v_emp_product t2 "
//                                + "on t1.id = t2.productid and t2.userid = @userid";
//            }
//
//        }
//
//        String type = cfg.getRightsControlType();
//        if (!StrUtils.isEmpty(type)) {
//            RightsControlType rct = RightsControlType.valueOf(type);
//            IMobileRightsController mrc = rct.createMobileController(paramsMap);
//
//            sqlValue = mrc.createQuerySql(sqlValue, cfg.getTestParameter());
//        }
//
//        Sql sql = Sqls.create(sqlValue);
//
//        String[] paraArr = cfg.getParameterArr();
//        for (String p : paraArr) {
//            sql.params().set(p, paramsMap.get(p));
//        }
//
//        sql.params()
//           .set(StandardParameters.COMM_TENANT_ID, paramsMap.get(StandardParameters.COMM_TENANT_ID))
//           .set(StandardParameters.COMM_USER_ID, paramsMap.get(StandardParameters.COMM_USER_ID));
//
//        return sql;
//    }
//
//    /**
//     * 获取手机端下拉数据
//     * 
//     * @param sourceCode 源code
//     * @param paramsMap 参数列表
//     * @return 返回下拉数据
//     */
//    public static Object getMobiDropDownData(String sourceCode, Map<String, Object> paramsMap) {
//        Dao dao = DbUtils.newDao();
//        return getMobiDropDownData(dao, sourceCode, paramsMap);
//    }
//
//    /**
//     * 获取手机端下拉数据
//     * 
//     * @param dao dao
//     * @param sourceCode 源code
//     * @param paramsMap 参数列表
//     * @return 返回下拉数据
//     */
//    public static Object getMobiDropDownData(Dao dao, String sourceCode, Map<String, Object> paramsMap) {
//        DropDownForMobile cfg = dao.fetch(DropDownForMobile.class, sourceCode);
//
//        return getSQLOutput(dao, cfg, paramsMap);
//    }
//}
//
///**
// * 
// * cfgDropDown callBack
// * 
// * @version 1.0
// * @author <a href="ttan_style@sina.cn">ttan</a>
// * @since 销售宝 2.0
// * 
// *        <pre>
// * 历史：
// *      建立: 2014年6月25日  ttan
// * </pre>
// */
//class MobileDropDownCallback implements SqlCallback {
//    /**
//     * SqlData配置
//     */
//    private DropDownForMobile config;
//
//    /**
//     * 构造函数
//     * 
//     * @param config 配置
//     */
//    public MobileDropDownCallback(DropDownForMobile config) {
//        this.config = config;
//    }
//
//    @Override
//    public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
//        JSONArray items = new JSONArray();
//
//        List<String> fields = config.getOutFieldList();
//        if (fields.isEmpty()) {
//            fields = config.getOutFieldList(rs.getMetaData());
//        }
//
//        while (rs.next()) {
//            JSONObject item = new JSONObject();
//            for (String f : fields) {
//                Object v = rs.getObject(f);
//                if (v == null) {
//                    item.put(f, "");
//                } else {
//                    if (java.sql.Clob.class.isAssignableFrom(v.getClass())) {
//                        item.put(f, StrUtils.c2str(v));
//                    } else {
//                        item.put(f, v);
//                    }
//                }
//            }
//
//            items.add(item);
//        }
//
//        return items;
//    }
//}
