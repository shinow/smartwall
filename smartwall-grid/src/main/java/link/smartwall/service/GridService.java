package link.smartwall.service;

import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itfsm.base.api.Result;
import com.itfsm.service.BaseService;
import com.itfsm.util.StrUtils;

import link.smartwall.grid.entity.ConfGrid;
import link.smartwall.grid.entity.ConfGridAction;
import link.smartwall.grid.entity.ConfGridCol;
import link.smartwall.grid.entity.ConfGridFilter;

@IocBean(args = {"refer:dao"})
public class GridService extends BaseService {

    public GridService(Dao dao) {
        super(dao);
    }

    /**
     * 获取所有配置
     * 
     * @param code
     * @return
     */
    public Result getModelByCode(String code) {
        try {
            ConfGrid cgc = dao().fetch(ConfGrid.class, Cnd.where("code", "=", code));
            dao().fetchLinks(cgc, "columns", Cnd.orderBy().asc("showIndex"));
            dao().fetchLinks(cgc, "actions", Cnd.orderBy().asc("showIndex"));
            dao().fetchLinks(cgc, "filters", Cnd.orderBy().asc("showIndex"));
            return Result.successResult(cgc);
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 获取配置信息
     * 
     * @param guid
     * @return
     */
    public Result getModel(String guid) {
        // TODO Auto-generated method stub.
        try {
            Record record = dao().fetch("conf_grid", Cnd.where("guid", "=", guid));
            return Result.successResult(record);
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 解析列
     * 
     * @param model
     * @return
     */
    public JSONArray extractModelColumns(ConfGrid model) {
        JSONArray jsa = new JSONArray();
        for (ConfGridCol gc : model.getColumns()) {
            JSONObject jso = new JSONObject();
            jso.put("code", gc.getCode());
            jso.put("caption", gc.getCaption());
            jso.put("align", gc.getAlign().toString());
            jso.put("width", gc.getWidth());
            jso.put("type", gc.getType());
            jso.put("visible", gc.isVisible());
            jso.put("valueFunc", gc.getValueSetFunc());
            jsa.add(jso);
        }
        return jsa;
    }

    /**
     * 解析按钮
     * 
     * @param model
     * @return
     */
    public Object extractModelButtons(ConfGrid model) {

        JSONArray jsa = new JSONArray();
        List<ConfGridAction> actions = model.getActions();
        for (ConfGridAction act : actions) {
            jsa.add(generateAction(act, 0));
        }

        return jsa;

    }

//    /**
//     * 解析查询条件
//     * 
//     * @param model
//     * @return
//     */
//    public JSONArray extractModelFilters(ConfGrid model) {
//        JSONArray jsa = new JSONArray();
//        for (ConfGridFilter fi : model.getFilters()) {
//            JSONObject jso = new JSONObject();
//            jso.put("caption", fi.getCaption());
//            jso.put("edit", );
//            jso.put("code", fi.getField());
//            jso.put("operator", fi.getOperator());
//            jso.put("datasource", fi.getDatasource());
//
//            jsa.add(jso);
//        }
//
//        return jsa;
//    }

    /**
     * 解析参数
     * 
     * @param model
     * @return
     */
    public JSONObject extractModelParams(ConfGrid model) {
        JSONObject jso = new JSONObject();
        jso.put("selectable", model.isSelectable());
        return jso;
    }

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
        jso.put("action", act.getFuncGuid());
        jso.put("caption", act.getCaption());
        jso.put("menu", act.getMenuButton());

        return jso;
    }

    /**
     * 获取指定列配置详情
     * 
     * @param code
     * @return
     */
    public Result getCol(String guid) {
        // TODO Auto-generated method stub.
        try {
            Record record = dao().fetch("conf_grid_col", Cnd.where("guid", "=", guid));
            return Result.successResult(record);
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 获取指定过滤配置详情
     * 
     * @param code
     * @return
     */
    public Result getFilter(String guid) {
        try {
            Record record = dao().fetch("conf_grid_filter", Cnd.where("guid", "=", guid));
            return Result.successResult(record);
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 获取指定操作配置详情
     * 
     * @param code
     * @return
     */
    public Result getAction(String guid) {
        // TODO Auto-generated method stub
        try {
            Record record = dao().fetch("conf_grid_action", Cnd.where("guid", "=", guid));
            return Result.successResult(record);
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 保存基础配置
     * 
     * @param code
     * @param data
     */
    public Result saveGridBaseConf(String guid, Map<String, Object> data) {
        // TODO Auto-generated method stub
        try {
            if (null == guid || guid.isEmpty()) {
                // 新增
                data.put(".table", "conf_grid");
                data.put("guid", StrUtils.uuid());
                dao().insert(data);

            } else {
                data.put(".table", "conf_grid");
                data.put("*guid", guid);
                data.put("guid", guid);
                dao().update(data);
            }
            return Result.successResult();
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 保存列配置
     * 
     * @param guid
     * @param jo
     * @return
     */
    public Result saveGridCol(String guid, JSONObject data) {
        // TODO Auto-generated method stub
        try {
            if (null == guid || guid.isEmpty()) {
                // 新增
                data.put(".table", "conf_grid_col");
                data.put("guid", StrUtils.uuid());
                dao().insert(data);

            } else {
                data.put(".table", "conf_grid_col");
                data.put("*guid", guid);
                data.put("guid", guid);
                dao().update(data);
            }
            return Result.successResult();
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 保存过滤配置
     * 
     * @param guid
     * @param jo
     * @return
     */

    public Result saveGridFilter(String guid, JSONObject data) {
        // TODO Auto-generated method stub
        try {
            if (null == guid || guid.isEmpty()) {
                // 新增
                data.put(".table", "conf_grid_filter");
                data.put("guid", StrUtils.uuid());
                dao().insert(data);

            } else {
                data.put(".table", "conf_grid_filter");
                data.put("*guid", guid);
                data.put("guid", guid);
                dao().update(data);
            }
            return Result.successResult();
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 保存操作配置
     * 
     * @param guid
     * @param jo
     * @return
     */
    public Result saveGridAction(String guid, JSONObject data) {
        // TODO Auto-generated method stub
        try {
            if (null == guid || guid.isEmpty()) {
                // 新增
                data.put(".table", "conf_grid_action");
                data.put("guid", StrUtils.uuid());
                dao().insert(data);

            } else {
                data.put(".table", "conf_grid_action");
                data.put("*guid", guid);
                data.put("guid", guid);
                dao().update(data);
            }
            return Result.successResult();
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 删除网格配置及网格下所有配置
     * 
     * @param guid
     * @return
     */
    public Result deleteGrid(String guid) {
        try {
            final String masterGuid = guid;
            Trans.exec(new Atom() {

                @Override
                public void run() {
                    dao().delete(ConfGrid.class, masterGuid);
                    Sql sql = Sqls.create("delete from conf_grid_col where master_guid =@guid");
                    sql.params().set("guid", masterGuid);
                    Sql sql1 = Sqls.create("delete from conf_grid_filter where master_guid =@guid");
                    sql1.params().set("guid", masterGuid);
                    Sql sql2 = Sqls.create("delete from conf_grid_action where master_guid =@guid");
                    sql2.params().set("guid", masterGuid);
                    dao().execute(sql);
                    dao().execute(sql1);
                    dao().execute(sql2);
                }
            });
            return Result.successResult();
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 删除列配置
     * 
     * @param guid
     * @return
     */
    public Result deleteGridCol(String guid) {
        // TODO Auto-generated method stub
        try {
            dao().delete(ConfGridCol.class, guid);
            return Result.successResult();
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 删除操作配置
     * 
     * @param guid
     * @return
     */
    public Result deleteGridAction(String guid) {
        // TODO Auto-generated method stub
        try {
            dao().delete(ConfGridAction.class, guid);
            return Result.successResult();
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 删除过滤配置
     * 
     * @param guid
     * @return
     */
    public Result deleteGridFilter(String guid) {
        // TODO Auto-generated method stub
        try {
            dao().delete(ConfGridFilter.class, guid);
            return Result.successResult();
        } catch (Exception e) {
            // TODO: handle exception
            return Result.failureResult(e.getMessage());
        }
    }

}
