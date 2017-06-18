package link.smartwall.web.controller.grid;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import link.smartwall.base.api.Result;
import link.smartwall.grid.entity.ConfFuncWorkFlow;
import link.smartwall.service.FireFlowService;
/**
 * 工作流
 * @author caisj
 *
 */
@At("/form/confs")
@IocBean
public class FireFlowController {

	@Inject
	private FireFlowService fireFlowService;

	/**
	 * 设计器跳转页面
	 * @param req
	 * @return
	 */
	@At("/data/addFlow")
	@Ok("fm:/sfa/management/editor/flowIndex.html")
	public Map<String, Object> addFlow(HttpServletRequest req) {
		Map<String, Object> result = new HashMap<String, Object>();
		return result;
	}

	/**
	 * 编辑工作流
	 * 
	 * @param req
	 * @return
	 */
	@At("/data/editFlow")
	@Ok("fm:/sfa/management/editor/flowIndex.html")
	public Map<String, Object> editFlow(@Param("guid") String guid) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("form", fireFlowService.getForm(guid));
		return result;
	}

	/**
	 * 删除工作流
	 * 
	 * @param id
	 * @return
	 */
	@At("/data/delFlow")
	@Ok("json")
	public Result delFlow(@Param("guid") String guid) {
		try {
			fireFlowService.delFlow(guid);
			return Result.successResult();
		} catch (Exception e) {
			return Result.failureResult("删除失败:" + e.getMessage());
		}
	}
	/*@At("/data/getConfData")
	@Ok("json")
	public Map<String, Object> getConfData(){
		return fireFlowService.getConfData();
	}*/
	/**
	 * 操作流程功能配置
	 * 
	 * @param confFuncWorkFlow
	 * @return
	 */
	@At("/data/editFun")
	@Ok("json")
	public Result saveOrUpdateFun(@Param("..") ConfFuncWorkFlow confFuncWorkFlow) {
		try {
			if (null != confFuncWorkFlow) {
				fireFlowService.saveOrUpdateFun(confFuncWorkFlow);
			}
			return Result.successResult();
		} catch (Exception e) {
			return Result.failureResult("操作失败：" + e.getMessage());
		}
	}
	/**
	 * 删除流程功能配置
	 * @param confFuncWorkFlow
	 * @return
	 */
	@At("/data/delFun")
	@Ok("json")
	public Result delfun(@Param("..") ConfFuncWorkFlow confFuncWorkFlow){
		try {
			fireFlowService.delFun(confFuncWorkFlow);
			return Result.successResult();
		} catch (Exception e) {
			return Result.failureResult(e.getMessage());
		}
	}
}
