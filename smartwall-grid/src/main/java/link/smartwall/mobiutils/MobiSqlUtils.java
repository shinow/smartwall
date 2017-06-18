package link.smartwall.mobiutils;
///**
// * SmartWall(2013)
// */
//package com.itfsm.mobiutils;
//
//import java.util.Map;
//
//import org.nutz.dao.Cnd;
//import org.nutz.dao.Sqls;
//import org.nutz.dao.sql.Sql;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.dcits.data.provider.entity.SqlDataConfig;
//import com.dcits.data.provider.entity.SqlDataConfigRight;
//import com.dcits.xsb.db.DbUtils;
//import com.itfsm.base.http.parameter.StandardParameters;
//import com.itfsm.base.util.StrUtils;
//import com.itfsm.grid.controller.RightsControlType;
//import com.itfsm.grid.controller.rights.IMobileRightsController;
//import com.itfsm.jsonobj.QueryDataObject;
//
///**
// * SQL辅助类
// * 
// * @version 1.0
// * @author <a herf="lexloo@gmail.com">lexloo</a>
// * @since 外勤助手 2.0
// * 
// *        <pre>
// * 历史：
// *      建立: 2013-10-15 lexloo
// * </pre>
// */
//public final class MobiSqlUtils {
//    /**
//     * 日志对象
//     */
//    private static final Logger LOG = LoggerFactory.getLogger(MobiSqlUtils.class.getName());
//
//    /**
//     * 构造函数
//     */
//    private MobiSqlUtils() {
//        // 无输出
//    }
//
//    /**
//     * 获取手机端查询SQL
//     * 
//     * @param config 配置信息
//     * @param parameter 参数
//     * @return SQL查询对象
//     */
//    public static Sql getMobiQuerySql(SqlDataConfig config, Map<String, Object> parameter) {
//        Sql sql = Sqls.create(getMobiQuerySqlString(config, null, parameter));
//
//        // 设置参数
//        String[] queryParams = config.getQueryParams();
//        for (String qp : queryParams) {
//            sql.params().set(qp, parameter.get(qp));
//        }
//
//        sql.params()
//           .set(StandardParameters.COMM_TENANT_ID, parameter.get(StandardParameters.COMM_TENANT_ID))
//           .set(StandardParameters.COMM_USER_ID, parameter.get(StandardParameters.COMM_USER_ID));
//
//        return sql;
//    }
//
//    /**
//     * 获取手机端查询SQL
//     * 
//     * @param config 配置信息
//     * @param queryDataObj 接口传递对象
//     * @param parameter 参数
//     * @return SQL查询对象
//     */
//    public static Sql getMobiQuerySql2(SqlDataConfig config, QueryDataObject queryDataObj, Map<String, Object> parameter) {
//        Sql sql = Sqls.create(getMobiQuerySqlString(config, queryDataObj, parameter));
//
//        // 设置参数
//        String[] queryParams = config.getQueryParams();
//        for (String qp : queryParams) {
//            sql.params().set(qp, parameter.get(qp));
//        }
//
//        sql.params()
//           .set(StandardParameters.COMM_TENANT_ID, parameter.get(StandardParameters.COMM_TENANT_ID))
//           .set(StandardParameters.COMM_USER_ID, parameter.get(StandardParameters.COMM_USER_ID));
//
//        return sql;
//    }
//
//    /**
//     * 获取移动端查询SQL
//     * 
//     * @param config 查询配置
//     * @param queryDataObj 接口传递对象
//     * @param parameter 参数配置
//     * @return SQL
//     */
//    private static String getMobiQuerySqlString(SqlDataConfig config,
//                                                QueryDataObject queryDataObj,
//                                                Map<String, Object> parameter) {
//        if (config.isSplitSQL()) {
//            return getSqlStrWithSegment(config, queryDataObj, parameter);
//        } else {
//            return getSqlStrWithContent(config, queryDataObj, parameter);
//        }
//    }
//
//    /**
//     * 整体SQL配置
//     * 
//     * @param config 配置信息
//     * @param queryDataObj 接口传输对象
//     * @param parameter 参数配置
//     * @return SQL语句
//     */
//    private static String getSqlStrWithContent(SqlDataConfig config,
//                                               QueryDataObject queryDataObj,
//                                               Map<String, Object> parameter) {
//        String strSql = config.getContent();
//
//        // 手机端若传递权限控制，优先
//        if (queryDataObj != null && !StrUtils.isEmpty(queryDataObj.getAuthorization())) {
//            RightsControlType rct = RightsControlType.valueOf(queryDataObj.getAuthorization());
//            IMobileRightsController mrc = rct.createMobileController(parameter);
//
//            strSql = mrc.createQuerySql(strSql);
//        } else {
//            String code = "rights_ctl_type_" + config.getCode();
//
//            SqlDataConfigRight right =
//                    DbUtils.newDao().fetch(SqlDataConfigRight.class,
//                                           Cnd.where("code", "=", code)
//                                              .and("tenantid", "=", parameter.get(StandardParameters.COMM_TENANT_ID)));
//
//            if (right != null) {
//                String rightType = right.getParameter().toUpperCase().trim();
//                RightsControlType rct = RightsControlType.valueOf(rightType);
//                IMobileRightsController mrc = rct.createMobileController(parameter);
//
//                strSql = mrc.createQuerySql(strSql);
//            }
//        }
//
//        // TODO 是否可以优化SQL
//        if (strSql.indexOf("$condition") == -1) {
//            String fields = config.getOutField();
//            if (StrUtils.isEmpty(fields)) {
//                strSql = "select * from (" + strSql + ") $condition";
//            } else {
//                if (config.isDistinct()) {
//                    strSql = "select distinct " + fields + " from (" + strSql + ") $condition";
//                } else {
//                    strSql = "select " + fields + " from (" + strSql + ") $condition";
//                }
//            }
//        }
//
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("SQL:");
//            LOG.debug(strSql);
//        }
//        return strSql;
//    }
//
//    /**
//     * 通过各SQL段组装SQL
//     * 
//     * @param config 配置信息
//     * @param queryDataObj 接口对象
//     * @param parameter 参数配置
//     * @return SQL语句
//     */
//    private static String getSqlStrWithSegment(SqlDataConfig config,
//                                               QueryDataObject queryDataObj,
//                                               Map<String, Object> parameter) {
//        String strSql = config.getFrom();
//        // 手机端若传递权限控制，优先
//        if (queryDataObj != null && !StrUtils.isEmpty(queryDataObj.getAuthorization())) {
//            RightsControlType rct = RightsControlType.valueOf(queryDataObj.getAuthorization());
//            IMobileRightsController mrc = rct.createMobileController(parameter);
//
//            strSql = mrc.createQuerySql(strSql);
//        } else {
//            String code = "rights_ctl_type_" + config.getCode();
//
//            SqlDataConfigRight right =
//                    DbUtils.newDao().fetch(SqlDataConfigRight.class,
//                                           Cnd.where("code", "=", code)
//                                              .and("tenantid", "=", parameter.get(StandardParameters.COMM_TENANT_ID)));
//
//            if (right != null) {
//                String rightType = right.getParameter().toUpperCase().trim();
//                RightsControlType rct = RightsControlType.valueOf(rightType);
//                IMobileRightsController mrc = rct.createMobileController(parameter);
//
//                strSql = mrc.createQuerySql(strSql);
//            }
//        }
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("select ").append(config.getSelect()).append(" from (").append(strSql).append(")");
//
//        if (!StrUtils.isEmpty(config.getWhere())) {
//            sb.append(" where ").append(config.getWhere());
//        }
//
//        if (!StrUtils.isEmpty(config.getGroupby())) {
//            sb.append(" group by ").append(config.getGroupby());
//        }
//
//        if (!StrUtils.isEmpty(config.getOrderby())) {
//            sb.append(" order by ").append(config.getOrderby());
//        }
//
//        strSql = sb.toString();
//
//        if (strSql.indexOf("$condition") == -1) {
//            String fields = config.getOutField();
//            if (StrUtils.isEmpty(fields)) {
//                strSql = "select * from (" + strSql + ") $condition";
//            } else {
//                strSql = "select " + fields + " from (" + strSql + ") $condition";
//            }
//        }
//
//        if (LOG.isDebugEnabled()) {
//            LOG.debug("SQL:");
//            LOG.debug(strSql);
//        }
//
//        return strSql;
//    }
//
//    /**
//     * 获取手机端数据字典查询SQL
//     * 
//     * @param interfaceCode 接口code
//     * @param parameter 参数
//     * @return SQL查询对象
//     */
//    public static Sql getDictOutput(String interfaceCode, Map<String, Object> parameter) {
//        Sql sql =
//                Sqls.create("select id, name from (select t2.id, t2.caption name, t2.tenantid "
//                            + " from tenant_dict t1 left join tenant_dict_dt t2 "
//                            + " on t1.id = t2.dict_id where t1.code = @code "
//                            + " order by t2.code) where tenantid = @tenantid");
//
//        sql.params().set("code", interfaceCode).set("tenantid", parameter.get(StandardParameters.COMM_TENANT_ID));
//
//        return sql;
//    }
//}
