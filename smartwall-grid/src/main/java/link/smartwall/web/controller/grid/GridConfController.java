/**
 * 天恒众航(2013)
 */
package link.smartwall.web.controller.grid;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import link.smartwall.base.api.Result;
import link.smartwall.service.GridService;

/**
 * Grid对象，MVC框架支持对象
 * 
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @version 1.0
 * @since 销售宝 2.0
 *        <p/>
 * 
 *        <pre>
 * 历史：
 * 建立: 2013-8-13 lexloo
 *        </pre>
 */
@At("/grid/confs")
@IocBean
public class GridConfController {
	@Inject
	private GridService gridService;
    // /**
    // * 获取所有配置
    // * @param code
    // * @return
    // */
    // @At("/data")
    // @Ok("json")
    // public Result gridData(@Param("code") String code) {
    // Result result = gridService.getModelByCode(code);
    // ConfGrid model = (ConfGrid) result.getMessage();
    // JSONObject json = new JSONObject();
    //
    // JSONObject base = new JSONObject();
    // base.put("title", model.getCaption());
    // json.put("base", base);
    //
    // json.put("columns", gridService.extractModelColumns( model));
    // json.put("buttons", gridService.extractModelButtons(model));
    // json.put("filters", gridService.extractModelFilters( model));
    // json.put("params", gridService.extractModelParams( model));
    // return Result.successResult(json);
    // }
    
    /**
     * 获取基础配置
     * @param code
     * @return
     */
    @At("/data/base")
    @Ok("json")
    public Result gridDataBase(@Param("guid") String guid) {
        return gridService.getModel(guid);
    }
    
    
    /**
     * 获取列配置
     * @param guid
     * @return
     */
    @At("/data/col")
    @Ok("json")
    public Result gridDataCol(@Param("guid") String guid) {
    	return gridService.getCol(guid);
    }
    
    /**
     * 获取过滤配置
     * @param guid
     * @return
     */
    @At("/data/filter")
    @Ok("json")
    public Result gridDataFilter(@Param("guid") String guid) {
    	return gridService.getFilter(guid);
    }
    
    /**
     * 获取操作配置
     * @param guid
     * @return
     */
    @At("/data/action")
    @Ok("json")
    public Result gridDataAction(@Param("guid") String guid) {
    	return gridService.getAction(guid);
    }
    

    /**
     * 保存网格基础配置
     * 
     * @param guid 网格guid
     * @param data 配置数据
     * @return 网格配置
     */
    @At("/save/base")
    @Ok("json")
    public Result saveGridBase(@Param("guid") String guid, @Param("data") String data) {
    	JSONObject jo = JSON.parseObject(data);
        return gridService.saveGridBaseConf(guid,jo);
    }
    
    /**
     * 保存列配置
     * 
     * @param guid 网格guid
     * @param data 配置数据
     * @return 
     */
    @At("/save/col")
    @Ok("json")
    public Result saveGridCol(@Param("guid") String guid, @Param("data") String data) {
    	JSONObject jo = JSON.parseObject(data);
        return gridService.saveGridCol(guid,jo);
    }
    
    /**
     * 保存过滤配置
     * 
     * @param guid 网格guid
     * @param data 配置数据
     * @return 
     */
    @At("/save/filter")
    @Ok("json")
    public Result saveGridFilter(@Param("guid") String guid, @Param("data") String data) {
    	JSONObject jo = JSON.parseObject(data);
        return gridService.saveGridFilter(guid,jo);
    }
    
    
    /**
     * 保存操作配置
     * 
     * @param guid 网格guid
     * @param data 配置数据
     * @return 
     */
    @At("/save/action")
    @Ok("json")
    public Result saveGridAction(@Param("guid") String guid, @Param("data") String data) {
    	JSONObject jo = JSON.parseObject(data);
        return gridService.saveGridAction(guid,jo);
    }
    
    
    /**
     * 删除操作配置
     * 
     * @param guid guid
     * @return 
     */
    @At("/delete/grid")
    @Ok("json")
    public Result deleteGrid(@Param("guid") String guid) {
        return gridService.deleteGrid(guid);
    }
    
    /**
     * 删除列配置
     * 
     * @param guid guid
     * @return 
     */
    @At("/delete/col")
    @Ok("json")
    public Result deleteGridCol( @Param("guid") String guid) {
        return gridService.deleteGridCol(guid);
    }
    
    /**
     * 删除操作配置
     * 
     * @param guid guid
     * @return 
     */
    @At("/delete/action")
    @Ok("json")
    public Result deleteGridAction( @Param("guid") String guid) {
        return gridService.deleteGridAction(guid);
    }
    
    /**
     * 删除过滤配置
     * 
     * @param guid guid
     * @return 
     */
    @At("/delete/filter")
    @Ok("json")
    public Result deleteGridFilter( @Param("guid") String guid) {
        return gridService.deleteGridFilter(guid);
    }
}
