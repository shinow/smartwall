package link.smartwall.web.controller;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import link.smartwall.base.api.Result;
import link.smartwall.base.http.UserManager;
import link.smartwall.service.CommonObjectService;
import link.smartwall.util.StrUtils;

/**
 * 主页面
 *
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @version 1.0
 * @since 销售宝 2.0
 *        <p>
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-8-13 lexloo
 *        </pre>
 */
@IocBean
@At("/common")
public class CommonObjectController {
    @Inject
    private CommonObjectService commonObjectService;

    @At("/queryByPager")
    @Ok("json")
    public Result queryByPager(@Param("data") String data,
                               @Param("pageNumber") Integer pageNumber,
                               @Param("pageSize") int pageSize) {
        try {
            JSONObject jo = JSON.parseObject(data);
            if (jo != null) {
                String code = jo.getString("code");
                Map<String, Object> values = jo.getJSONObject("values");
                if (StrUtils.isEmpty(code)) {
                    return Result.failureResult("query参数错误");
                }
                return commonObjectService.queryByPager(code, values, pageNumber, pageSize);
            }
            return Result.failureResult("查询失败");
        } catch (Exception e) {
            return Result.failureResult("query操作失败");
        }
    }

    @At("/query")
    @Ok("json")
    public Result query(@Param("data") String data) {
        try {
            JSONObject jo = JSON.parseObject(data);
            if (jo != null) {
                String code = jo.getString("code");
                Map<String, Object> values = jo.getJSONObject("values");
                if (StrUtils.isEmpty(code)) {
                    return Result.failureResult("query参数错误");
                }
                return commonObjectService.query(code, values);
            }
            return Result.failureResult("查询失败");
        } catch (Exception e) {
            return Result.failureResult("query操作失败");
        }
    }

    /**
     * 根据表名查询
     *
     * @param data
     * @return
     */
    @At("/queryByTable")
    @Ok("json")
    public Result queryByTable(@Param("data") String data) {
        JSONObject jo = JSON.parseObject(data);
        if (jo != null) {
            String code = jo.getString("code");
            Map<String, Object> values = jo.getJSONObject("values");
            if (StrUtils.isEmpty(code)) {
                return Result.failureResult("query参数错误");
            }
            return commonObjectService.queryByTable(code, values);
        }
        return Result.failureResult("查询失败");
    }

    @At("/insert")
    @Ok("json")
    public Result insert(@Param("data") String data) {
        try {
            JSONObject jo = JSON.parseObject(data);
            if (jo != null) {
                /**
                 * 增加一个参数is_extend 用于判断该对象有无设置自定义字段 yty 2017.4.7
                 */
                Boolean isExtend = jo.getBoolean("is_extend");
                if (null == isExtend) {
                    isExtend = false;
                }
                String code = jo.getString("code");
                Map<String, Object> values = jo.getJSONObject("values");
                if (StrUtils.isEmpty(code) || null == values || values.isEmpty()) {
                    return Result.failureResult("insert参数错误");
                }
                return commonObjectService.insert(code, values, isExtend);
            }
            return Result.failureResult("新增失败");
        } catch (Exception e) {
            /**
             * 违反唯一约束条件异常
             */
            if (null != e.getCause() && e.getCause().toString().contains("ORA-00001")) {
                return Result.failureResult("编码不能相同");
            }
            e.printStackTrace();
            return Result.failureResult(e.getMessage());
        }
    }

    @At("/update")
    @Ok("json")
    public Result update(@Param("data") String data) {
       // System.out.println("---------------------------"+UserManager.getCurrentUser().getGuid());
        try {
            JSONObject jo = JSON.parseObject(data);
            if (jo != null) {
                /**
                 * 增加一个参数is_extend 用于判断该对象有无设置自定义字段 yty 2017.4.7
                 */
                Boolean isExtend = jo.getBoolean("is_extend");
                if (null == isExtend) {
                    isExtend = false;
                }
                String code = jo.getString("code");
                Map<String, Object> values = jo.getJSONObject("values");
                if (StrUtils.isEmpty(code) || null == values || values.isEmpty()) {
                    return Result.failureResult("update参数错误");
                }
                Result result = commonObjectService.update(isExtend, code, values);
                if (result.isSuccess()) {
                    return Result.successResult();
                } else {
                    return result;
                }
            }
            return Result.failureResult("更新失败");
        } catch (Exception e) {
            /**
             * 违反唯一约束条件异常
             */
            if (null != e.getCause() && e.getCause().toString().contains("ORA-00001")) {
                return Result.failureResult("编码不能相同");
            }
            e.printStackTrace();
            return Result.failureResult(e.getMessage());
        }
    }

    @At("/delete")
    @Ok("json")
    public Result deleteByGuid(@Param("data") String data) {
        try {
            JSONObject jo = JSON.parseObject(data);
            if (jo != null) {
                String code = jo.getString("code");
                String guid = jo.getString("guid");
                if (StrUtils.isEmpty(code) || StrUtils.isEmpty(guid)) {
                    return Result.failureResult("delete参数错误");
                }
                return commonObjectService.deleteByGuid(code, guid);
            }
            return Result.failureResult("删除失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failureResult(e.getMessage());
        }
    }

    /**
     * 根据guid和表名批量删除
     *
     * @param data
     * @return
     */
    @At("/batchDel")
    @Ok("json")
    public Result batchDelByGuid(@Param("data") String data) {
        try {
            JSONObject jo = JSON.parseObject(data);
            if (jo != null) {
                String tName = jo.getString("tName");
                JSONArray guids = jo.getJSONArray("guids");
                commonObjectService.batchDelByGuid(tName, guids);
            }
            return Result.successResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failureResult("批量删除失败");
        }
    }

    @At("/getData")
    @Ok("json")
    public Result getDataByGuid(@Param("data") String data) {
        try {
            JSONObject jo = JSON.parseObject(data);
            if (jo != null) {
                /**
                 * 增加一个参数is_extend 用于判断该对象有无设置自定义字段 yty 2017.4.7
                 */
                Boolean isExtend = jo.getBoolean("is_extend");
                if (null == isExtend) {
                    isExtend = false;
                }
                String code = jo.getString("code");
                String guid = jo.getString("guid");
                if (StrUtils.isEmpty(code) || StrUtils.isEmpty(guid)) {
                    return Result.failureResult("查询数据出错");
                }
                return commonObjectService.getDataByGuid(isExtend, code, guid);
            }
            return Result.failureResult("查询数据出错");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failureResult("查询失败");
        }
    }

}