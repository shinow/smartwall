package link.smartwall.service;
///**
// * SmartWall(2015)
// */
//package com.itfsm.web.control.service;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
//import org.nutz.dao.Cnd;
//import org.nutz.dao.Dao;
//import org.nutz.dao.Sqls;
//import org.nutz.dao.pager.Pager;
//import org.nutz.dao.sql.Sql;
//import org.nutz.dao.sql.SqlCallback;
//import org.nutz.trans.Atom;
//import org.nutz.trans.Trans;
//
//import com.alibaba.fastjson.JSONObject;
//import com.dcits.xsb.db.DbUtils;
//import com.itfms.util.SqlUtils;
//import com.itfsm.base.http.UserManager;
//import com.itfsm.base.http.WebUser;
//import com.itfsm.base.http.parameter.StandardParameters;
//import com.itfsm.base.util.StrUtils;
//import com.itfsm.grid.controller.PagerInfo;
//import com.itfsm.kernel.Kernel;
//import com.itfsm.kernel.event.ConfigChangeEvent;
//import com.itfsm.kernel.event.ConfigChangeEventDataObject;
//import com.itfsm.web.control.entity.GridSelectorEntity;
//
///**
// * 网格选择服务
// * 
// * @version 1.0
// * @author <a herf="lexloo@gmail.com">lexloo</a>
// * @since 外勤助手 4.0.0
// * 
// *        <pre>
// * 历史：
// *      2016年2月16日 lexloo * 建立
// * </pre>
// */
//public class GridSelectorServices {
//    /**
//     * 获取记录条数
//     */
//    private static final int DEFAULT_PAGE_SIZE = 10;
//
//    /**
//     * 新服务
//     * 
//     * @return 服务类
//     */
//    public static GridSelectorServices newServices() {
//        return new GridSelectorServices();
//    }
//
//    /**
//     * 获取配置
//     * 
//     * @param code 代码
//     * @param clientId clientId
//     * @return 配置信息
//     */
//    public JSONObject getConfig(String code, int clientId) {
//        Dao dao = DbUtils.newDao();
//        GridSelectorEntity gse = dao.fetch(GridSelectorEntity.class, code);
//
//        JSONObject config = JSONObject.parseObject(gse.getConfContent());
//        config.put("isTree", gse.isTree());
//
//        String sqlStr = gse.getSqlContent();
//        Sql sql = Sqls.create("select count(1) n from (" + sqlStr + ")");
//
//        WebUser wu = UserManager.getCurrentUser();
//        sql.params()
//           .set(StandardParameters.COMM_TENANT_ID, wu.getTenantId())
//           .set(StandardParameters.COMM_USER_ID, wu.getId())
//           .set(StandardParameters.COMM_DPET_ID, wu.getDepartmentId())
//           .set("clientid", clientId);
//        sql.setCallback(Sqls.callback.integer());
//
//        dao.execute(sql);
//        PagerInfo pagerInfo = new PagerInfo(1, DEFAULT_PAGE_SIZE);
//        pagerInfo.setRecordCount(sql.getInt());
//
//        config.put("pageInfo", pagerInfo);
//
//        // 查询所有选中的id
//        sql = Sqls.create("select id from (" + sqlStr + ") where checked = 1");
//        sql.params()
//           .set(StandardParameters.COMM_TENANT_ID, wu.getTenantId())
//           .set(StandardParameters.COMM_USER_ID, wu.getId())
//           .set(StandardParameters.COMM_DPET_ID, wu.getDepartmentId())
//           .set("clientid", clientId);
//        sql.setCallback(Sqls.callback.longs());
//        dao.execute(sql);
//
//        config.put("checkedIds", sql.getResult());
//
//        return config;
//    }
//
//    /**
//     * 获取分页信息
//     * 
//     * @param code 代码
//     * @param filter 过滤信息
//     * @param clientId clientId
//     * @return 分页信息
//     */
//    public PagerInfo getPageInfo(String code, String filter, int clientId) {
//        Dao dao = DbUtils.newDao();
//        GridSelectorEntity gse = dao.fetch(GridSelectorEntity.class, code);
//
//        String sqlStr = gse.getSqlContent();
//        Sql sql = Sqls.create("select count(1) n from (" + sqlStr + ") $condition");
//        if (!StrUtils.isEmpty(filter)) {
//            String[] arr = gse.getFilterFieldArr();
//            String fv = "%" + filter + "%";
//
//            Cnd cnd = Cnd.where(arr[0], "like", fv);
//            for (int i = 1, size = arr.length; i < size; i++) {
//                cnd.or(arr[i], "like", fv);
//            }
//
//            sql.setCondition(cnd);
//        }
//
//        WebUser wu = UserManager.getCurrentUser();
//        sql.params()
//           .set(StandardParameters.COMM_TENANT_ID, wu.getTenantId())
//           .set(StandardParameters.COMM_USER_ID, wu.getId())
//           .set(StandardParameters.COMM_DPET_ID, wu.getDepartmentId())
//           .set("clientid", clientId);
//        sql.setCallback(Sqls.callback.integer());
//
//        dao.execute(sql);
//        PagerInfo pagerInfo = new PagerInfo(1, DEFAULT_PAGE_SIZE);
//
//        pagerInfo.setRecordCount(sql.getInt());
//
//        return pagerInfo;
//    }
//
//    /**
//     * 获取数据
//     * 
//     * @param code 代码
//     * @param page 页数
//     * @param filter 过滤信息
//     * @param clientId clientId
//     * @return Xml数据
//     */
//    public String getData(String code, int page, String filter, int clientId) {
//        Dao dao = DbUtils.newDao();
//        GridSelectorEntity gse = dao.fetch(GridSelectorEntity.class, code);
//
//        String sqlStr = gse.getSqlContent();
//        Sql sql = Sqls.create("select * from (" + sqlStr + ") $condition");
//
//        WebUser wu = UserManager.getCurrentUser();
//        sql.params()
//           .set(StandardParameters.COMM_TENANT_ID, wu.getTenantId())
//           .set(StandardParameters.COMM_USER_ID, wu.getId())
//           .set(StandardParameters.COMM_DPET_ID, wu.getDepartmentId())
//           .set("clientid", clientId);
//
//        Pager pager = new Pager();
//        pager.setPageNumber(page);
//        pager.setPageSize(DEFAULT_PAGE_SIZE);
//
//        sql.setPager(pager);
//
//        if (!StrUtils.isEmpty(filter)) {
//            String[] arr = gse.getFilterFieldArr();
//            String fv = "%" + filter + "%";
//
//            Cnd cnd = Cnd.where(arr[0], "like", fv);
//            for (int i = 1, size = arr.length; i < size; i++) {
//                cnd.or(arr[i], "like", fv);
//            }
//
//            sql.setCondition(cnd);
//        }
//        sql.setCallback(new SqlCallback() {
//            public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
//                List<String> fields = SqlUtils.getLowerFieldNameArray(rs);
//                StringBuilder result = new StringBuilder("<root>");
//                while (rs.next()) {
//                    result.append("<item>");
//                    for (String f : fields) {
//                        result.append("<" + f + ">" + StrUtils.c2str(rs.getString(f), "") + "</" + f + ">");
//                    }
//                    result.append("</item>");
//                }
//
//                result.append("</root>");
//
//                return result.toString();
//            }
//        });
//
//        dao.execute(sql);
//
//        return sql.getString();
//    }
//
//    /**
//     * 获取数据
//     * 
//     * @param code 代码
//     * @param parent parentId
//     * @return Xml数据
//     */
//    public String getDataAll(String code, int parent) {
//        Dao dao = DbUtils.newDao();
//        GridSelectorEntity gse = dao.fetch(GridSelectorEntity.class, code);
//
//        String sqlStr = gse.getSqlContent();
//        Sql sql = Sqls.create("select * from (" + sqlStr + ") $condition");
//
//        WebUser wu = UserManager.getCurrentUser();
//        sql.params()
//           .set(StandardParameters.COMM_TENANT_ID, wu.getTenantId())
//           .set(StandardParameters.COMM_USER_ID, wu.getId())
//           .set(StandardParameters.COMM_DPET_ID, wu.getDepartmentId())
//           .set("parent", parent);
//
//        sql.setCallback(new SqlCallback() {
//            public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
//                List<String> fields = SqlUtils.getLowerFieldNameArray(rs);
//                StringBuilder result = new StringBuilder("<root>");
//                while (rs.next()) {
//                    result.append("<item>");
//                    for (String f : fields) {
//                        result.append("<" + f + ">" + StrUtils.c2str(rs.getString(f), "") + "</" + f + ">");
//                    }
//                    result.append("</item>");
//                }
//
//                result.append("</root>");
//
//                return result.toString();
//            }
//        });
//
//        dao.execute(sql);
//
//        return sql.getString();
//    }
//
//    /**
//     * 根据表名，Id值获取Name
//     * @param code 表名
//     * @param value id值
//     * @return name
//     */
//    public Object getIdValue(String code, String value) {
//        Dao dao = DbUtils.newDao();
//        GridSelectorEntity gse = dao.fetch(GridSelectorEntity.class, code);
//
//        String tableName = gse.getAuthTable();
//        Sql sql = Sqls.create("select name from $table where id = @id");
//        sql.params().set("id", value);
//        sql.vars().set("table", tableName);
//        sql.setCallback(Sqls.callback.str());
//        
//        dao.execute(sql);
//        
//        JSONObject rtn = new JSONObject();
//        rtn.put("name", sql.getResult());
//        
//        return rtn;
//    }
//    /**
//     * 获取配置树数据
//     * 
//     * @param code 树代码
//     * @return 树数据
//     */
//    public Object getTreeData(String code) {
//        Dao dao = DbUtils.newDao();
//        GridSelectorEntity gse = dao.fetch(GridSelectorEntity.class, code);
//
//        Sql sql = Sqls.create(gse.getTreeData());
//        WebUser wu = UserManager.getCurrentUser();
//
//        sql.setCallback(Sqls.callback.records());
//        sql.params().set("userid", wu.getId()).set("tenantid", wu.getTenantId());
//        dao.execute(sql);
//
//        return sql.getResult();
//    }
//
//    /**
//     * 保存配置信息
//     * 
//     * @param code 代码
//     * @param clientId 客户Id
//     * @param value 值
//     * @return 保存结果信息
//     */
//    public JSONObject saveAuthConfig(final String code, final int clientId, final String value) {
//        JSONObject jso = new JSONObject();
//        try {
//            Trans.exec(new Atom() {
//                @Override
//                public void run() {
//                    Dao dao = DbUtils.newDao();
//                    GridSelectorEntity gse = dao.fetch(GridSelectorEntity.class, code);
//                    int tenantId = UserManager.getCurrentUser().getTenantId();
//
//                    Sql sql = Sqls.create("delete from $table where clientid = @clientid and isrole = @isrole");
//                    sql.vars().set("table", gse.getAuthTable());
//                    sql.params().set("isrole", 0).set("clientid", clientId);
//                    dao.execute(sql);
//
//                    if (!StrUtils.isEmpty(value)) {
//                        sql =
//                                Sqls.create("insert into $table (id, clientid, isrole, valueid, tenantid) "
//                                            + "values ($seq, @clientid, @isrole, @valueid, @tenantid)");
//                        sql.vars().set("table", gse.getAuthTable());
//                        sql.vars().set("seq", gse.getAuthTable() + "_s.nextval");
//
//                        String[] values = value.split(",");
//                        boolean needExecute = false;
//                        for (String v : values) {
//                            sql.params()
//                               .set("isrole", 0)
//                               .set("clientid", clientId)
//                               .set("valueid", v)
//                               .set("tenantid", tenantId);
//                            sql.addBatch();
//
//                            needExecute = true;
//                        }
//
//                        if (needExecute) {
//                            dao.execute(sql);
//                        }
//                    }
//                }
//            });
//
//            Kernel.getInstance()
//                  .getEventBus()
//                  .fireKernelEvent(new ConfigChangeEvent(this, new ConfigChangeEventDataObject(code, clientId, value)));
//
//            jso.put("code", 0);
//        } catch (Exception ex) {
//            jso.put("code", 1);
//            jso.put("results", ex.getLocalizedMessage());
//        }
//
//        return jso;
//    }
//
//    /**
//     * 保存配置信息,clientId与ValueId交换
//     * 
//     * @param code 代码
//     * @param clientId 客户Id
//     * @param value 值
//     * @return 保存结果信息
//     */
//    public JSONObject saveAuthConfigReverse(final String code, final int clientId, final String value) {
//        JSONObject jso = new JSONObject();
//        try {
//            Trans.exec(new Atom() {
//                @Override
//                public void run() {
//                    Dao dao = DbUtils.newDao();
//                    GridSelectorEntity gse = dao.fetch(GridSelectorEntity.class, code);
//                    int tenantId = UserManager.getCurrentUser().getTenantId();
//
//                    Sql sql = Sqls.create("delete from $table where valueid = @clientid and isrole = @isrole");
//                    sql.vars().set("table", gse.getAuthTable());
//                    sql.params().set("isrole", 0);
//                    sql.params().set("clientid", clientId);
//                    dao.execute(sql);
//
//                    if (!StrUtils.isEmpty(value)) {
//                        sql =
//                                Sqls.create("insert into $table (id, clientid, isrole, valueid, tenantid) "
//                                            + "values ($seq, @clientid, @isrole, @valueid, @tenantid)");
//                        sql.vars().set("table", gse.getAuthTable());
//                        sql.vars().set("seq", gse.getAuthTable() + "_s.nextval");
//
//                        String[] values = value.split(",");
//
//                        boolean needExecute = false;
//                        for (String v : values) {
//                            sql.params()
//                               .set("isrole", 0)
//                               .set("valueid", clientId)
//                               .set("clientid", v)
//                               .set("tenantid", tenantId);
//                            sql.addBatch();
//
//                            needExecute = true;
//                        }
//
//                        if (needExecute) {
//                            dao.execute(sql);
//                        }
//                    }
//                }
//            });
//
//            jso.put("code", 0);
//        } catch (Exception ex) {
//            jso.put("code", 1);
//            jso.put("results", ex.getLocalizedMessage());
//        }
//
//        return jso;
//    }
//}
