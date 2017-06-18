/**
 * 天恒众航(2013)
 */
package link.smartwall.service.grid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.dao.util.cri.Exps;
import org.nutz.ioc.loader.annotation.IocBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import link.smartwall.base.http.UserManager;
import link.smartwall.cache.redis.RedisMgr;
import link.smartwall.grid.entity.ConfGrid;
import link.smartwall.grid.entity.ConfGridAction;
import link.smartwall.grid.entity.ConfGridCol;
import link.smartwall.grid.entity.ConfGridFilter;
import link.smartwall.service.BaseService;
import link.smartwall.util.StrUtils;

/**
 * Grid辅助类
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-1-11 lexloo
 *        </pre>
 */
@IocBean(args = {"refer:dao"})
public class GridHelper extends BaseService {
    /**
     * constructor
     */
    public GridHelper(Dao dao) {
        super(dao);
    }

    /**
     * 获取Grid模型
     * 
     * @param guid 模型代码
     * @param tenantId 企业Id
     * @return Grid模型
     */
    public ConfGrid getModel(String guid, int tenantId) {
        String cacheKey = guid + "_" + tenantId;
        String cache = RedisMgr.get(cacheKey);
        if (cache == null) {
            Dao dao = dao();
            ConfGrid model = dao.fetch(ConfGrid.class, Cnd.where("guid", "=", guid).and("data_is_deleted", "=", 0));// dao.fetch(ConfGrid.class,
                                                                                                                    // guid);
            model.updateColumns(dao);
            model.updateActions(dao);
            model.updateFilters(dao);

            // TODO 处理企业自定义
            RedisMgr.set(cacheKey, JSON.toJSONString(model));

            return model;
        } else {
            System.out.println("使用缓存");
            return JSON.toJavaObject(JSON.parseObject(cache), ConfGrid.class);
        }
    }

    /**
     * 获取企业功能集
     * 
     * @param tenantId 企业Id
     * @return 功能集
     */
    public Set<Integer> getTenantFuncs(int tenantId) {
        Dao dao = dao();
        List<Record> records = dao.query("v_rights_tenant_funcs", Cnd.where("tenantid", "=", tenantId));
        Set<Integer> rtn = new HashSet<Integer>();
        for (Record r : records) {
            rtn.add(r.getInt("funcid"));
        }

        return rtn;
    }

    /**
     * 企业参数管理器
     */
    // private static final TenantIndividuationParameterManager PM = TenantIndividuationParameterManager.INSTANCE;

    /**
     * 产生Action对象
     * 
     * @param act 操作
     * @param tenantId 企业id
     * @return Action对象
     */
    private static JSONObject generateAction(ConfGridAction act, int tenantId) {
        JSONObject jso = new JSONObject();

        // String customAction = PM.fillStringParams(act.getAction(), tenantId);

        jso.put("icon", act.getIcon());
        jso.put("action", act.getAction());
        jso.put("caption", act.getCaption());
        // jso.put("group", act.getGroupType());
        jso.put("menu", act.getMenuButton());
        // jso.put("validateArg", act.getValidateArg());
        // jso.put("validateArgValue", act.getValidateArgValue());

        return jso;
    }

    /**
     * 解析模型中的按钮信息，有权限控制
     * 
     * @param session session
     * @param model model
     * @return 按钮信息
     */
    public JSONArray extractModelButtons(HttpSession session, ConfGrid model) {
        JSONArray jsa = new JSONArray();
        // if (model.hasAction()) {
        // WebUser webUser = UserManager.getCurrentUser();
        List<ConfGridAction> actions = model.getActions();
        //
        // int tenantId = webUser.getTenantId();
        // if (webUser.isSuperUser()) {
        // Set<Integer> rights = this.getTenantFuncs(tenantId);
        for (ConfGridAction act : actions) {
            // if (act.getFuncId() == 0 || rights.contains(act.getFuncId()) || act.isVisible()) {
            jsa.add(generateAction(act, 0));
        }
        // }
        // } else {
        // @SuppressWarnings("unchecked")
        // Set<Integer> rights = (Set<Integer>) session.getAttribute(Constants.SESSION_RIGHTS);
        //
        // for (GridAction act : actions) {
        // if (act.getFuncId() == 0 || rights.contains(act.getFuncId()) || act.isVisible()) {
        // jsa.add(generateAction(act, tenantId));
        // }
        // }
        // }
        // }

        return jsa;
    }

    /**
     * 解析模型中的过滤信息
     * 
     * @param session session
     * @param model model
     * @return 过滤信息
     */
    public JSONArray extractModelFilters(HttpSession session, ConfGrid model) {
        JSONArray jsa = new JSONArray();
        for (ConfGridFilter fi : model.getFilters()) {
            JSONObject jso = new JSONObject();

            jso.put("caption", fi.getCaption());
            jso.put("code", fi.getField());
            jso.put("editor", fi.getEditor());
            jso.put("ds", fi.getDatasource());
            jso.put("op", fi.getOperator());
            jso.put("type", fi.getType());

            jsa.add(jso);
        }

        for (ConfGridCol gc : model.getColumns()) {
            if (!gc.isShowInFilter()) {
                continue;
            }

            JSONObject jso = new JSONObject();
            jso.put("caption", gc.getCaption());
            jso.put("code", gc.getCode());
            jso.put("editor", gc.getFilterEditor());
            jso.put("ds", gc.getFilterDataSource());
            jso.put("op", gc.getFilterOp());

            jsa.add(jso);
        }

        return jsa;
    }

    /**
     * 解析模型中的列信息
     * 
     * @param session session
     * @param model model
     * @return 列信息
     */
    public JSONArray extractModelColumns(HttpSession session, ConfGrid model) {
        JSONArray jsa = new JSONArray();
        for (ConfGridCol gc : model.getColumns()) {
            JSONObject jso = new JSONObject();
            jso.put("code", gc.getCode());
            jso.put("caption", gc.getCaption());
            jso.put("align", gc.getAlign().toString());
            jso.put("width", gc.getWidth());
            jso.put("type", gc.getType());
            jso.put("visible", gc.isVisible());
            // jso.put("canSort", gc.isCanSort());
            jso.put("valueFunc", gc.getValueSetFunc());
            jso.put("showInSuperFilter", gc.isShowInSuperFilter());

            jsa.add(jso);
        }

        return jsa;
    }

    /**
     * 获取所有列的类型
     * 
     * @param columns 所有列配置
     * @return 所有配置
     */
    private Map<String, String> getColumnType(List<ConfGridCol> columns) {
        Map<String, String> types = new HashMap<String, String>();

        for (ConfGridCol cgc : columns) {
            types.put(cgc.getCode().toLowerCase(), cgc.getType());
        }

        return types;
    }

    public JSONObject getData(String grid, Cnd cnd, String page, String pageSize, Map<String, Object> params) {
        Pager pager = createPager(page, pageSize);
        final ConfGrid gridModel = this.getModel(grid, 0);
        String sqlStr = gridModel.getDsContent();
        if (gridModel.isDeptControl()) {
            // 部门权限控制
            sqlStr = "select t1.* from ("
                     + sqlStr
                     + ") t1 inner join v_role_dept_emp t2 "
                     + "on t1.deptid = t2.deptid and t2.userid = @userid";
        }

        Sql sql = Sqls.create("select * from ( " + sqlStr + ") $condition");
        sql.setPager(pager);
        sql.setCondition(cnd);

        String paramsStr = gridModel.getParameters();
        if (!StrUtils.isEmpty(paramsStr)) {
            String[] paramArr = paramsStr.split(",");
            for (String param : paramArr) {
                sql.params().set(param, params.get(param));
            }
        }

        final Map<String, String> types = this.getColumnType(gridModel.getColumns());
        sql.setCallback(new SqlCallback() {

            @Override
            public Object invoke(Connection conn, ResultSet rs, Sql sql) throws SQLException {
                JSONArray arr = new JSONArray();

                while (rs.next()) {
                    JSONObject item = new JSONObject();
                    for (String field : gridModel.getFieldArr()) {
                        String code = field.trim();
                        String type = types.get(code);
                        if (type == null) {
                            item.put(code, rs.getString(code));
                        } else {
                            switch (type) {
                            case "STRING":
                                item.put(code, rs.getString(code));
                                break;
                            case "DATETIME":
                                item.put(code, rs.getTimestamp(code));
                                break;
                            case "DATE":
                                item.put(code, rs.getDate(code));
                                break;
                            default:
                                item.put(code, rs.getString(code));
                            }
                        }
                    }

                    arr.add(item);
                }

                return arr;
            }
        });

        Sql sql2 = Sqls.create("select count(1) from ( " + sqlStr + ") $condition");
        sql2.setCallback(Sqls.callback.longValue());
        sql2.setCondition(cnd);
        if (!StrUtils.isEmpty(paramsStr)) {
            String[] paramArr = paramsStr.split(",");
            for (String param : paramArr) {
                sql2.params().set(param, params.get(param));
            }
        }
        if (gridModel.isDeptControl()) {
            /**
             * set userid param
             */
//TODO
            sql.setParam("userid", UserManager.getCurrentUser().getGuid());
            sql2.setParam("userid", UserManager.getCurrentUser().getGuid());
        }
        dao().execute(sql, sql2);

        JSONObject rtn = new JSONObject();
        rtn.put("Rows", sql.getResult());
        rtn.put("Total", sql2.getInt());

        return rtn;
    }

    private Pager createPager(String page, String pageSize) {
        if (null != page) {
            return dao().createPager(Integer.parseInt(page), Integer.parseInt(pageSize));
        } else {
            return null;
        }
    }

    public JSONObject getChildData(String table, String mainGuid) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Rows", dao().query(table, Cnd.where(Exps.eq("main_guid", mainGuid))));
        return jsonObject;
    }


}