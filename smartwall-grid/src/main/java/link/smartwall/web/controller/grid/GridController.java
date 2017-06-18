/**
 * SmartWall(2013)
 */
package link.smartwall.web.controller.grid;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import link.smartwall.base.http.UserManager;
import link.smartwall.base.http.WebUser;
import link.smartwall.grid.entity.ConfGrid;
import link.smartwall.service.ExcelExportService;
import link.smartwall.service.grid.GridHelper;
import link.smartwall.util.DateUtils;
import link.smartwall.util.ServletUtils;
import link.smartwall.web.controller.grid.filter.BetweenOperator;
import link.smartwall.web.controller.grid.filter.IOperator;
import link.smartwall.web.controller.grid.filter.OperatorFactory;

/**
 * Grid对象，MVC框架支持对象
 * 
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @version 1.0
 * @since  2.0
 *        <p/>
 * 
 *        <pre>
 * 历史：
 * 建立: 2013-8-13 lexloo
 *        </pre>
 */
@At("/grid")
@IocBean
public class GridController {
	@Inject
	private GridHelper gridHelper;
	@Inject
	private ExcelExportService exportService;
	/**
	 * 默认显示20行
	 */
	public static final int ROWS = 20;

	/**
	 * 网格显示，显示第一页
	 * 
	 * @param grid
	 *            网格配置
	 * @return 页面
	 */
	@At("/show/?")
	@Ok("fm:/grid.html")
	public Map<String, Object> gridShow(String grid) {
		ConfGrid model = gridHelper.getModel(grid, 0);
		Map<String, Object> rtn = new HashMap<>();
		rtn.put("code", grid);
		rtn.put("useJsModel", model.getUseJsModule());

		return rtn;
	}

	@At("/showMongoose/?")
	@Ok("fm:/mongoose_grid.html")
	public Map<String, Object> gridMongoose(String grid) {
		ConfGrid model = gridHelper.getModel(grid, 0);
		Map<String, Object> rtn = new HashMap<>();
		rtn.put("code", grid);
		rtn.put("useJsModel", model.getUseJsModule());

		return rtn;
	}

	/**
	 * 网格显示，显示第一页
	 * 
	 * @param grid
	 *            网格配置
	 * @return 页面
	 */
	@At("/treeShow/?")
	@Ok("fm:/treeForm.html")
	public Map<String, Object> treeShow(String grid) {
		ConfGrid model = gridHelper.getModel(grid, 0);
		Map<String, Object> rtn = new HashMap<>();
		rtn.put("code", grid);
		rtn.put("useJsModel", model.getUseJsModule());

		return rtn;
	}

	@At("/tree/?/?")
	@Ok("fm:/treeGrid.html")
	public Map<String, Object> treeGrid(String grid, String treeId) {
		ConfGrid model = gridHelper.getModel(grid, 0);
		Map<String, Object> rtn = new HashMap<>();
		rtn.put("code", grid);
		rtn.put("treeId", treeId);
		rtn.put("useJsModel", model.getUseJsModule());

		return rtn;
	}

	/**
	 * 网格数据
	 * 
	 * @param grid
	 *            网格配置
	 * @return 网格数据
	 */
	@At("/data/?")
	@Ok("json")
	public JSONObject gridData(String grid, HttpServletRequest request) {
		String page = request.getParameter("page");
		String pageSize = request.getParameter("pagesize");
		System.out.print("\n\n\n\n\n\n\n");
		Map<String, Object> params = ServletUtils.parametersToMap(request);
		System.out.println(params);
		System.out.print("\n\n\n\n\n\n\n");

		Cnd cnd = this.extractFilters(request, gridHelper.getModel(grid, 0));

		return gridHelper.getData(grid, cnd, page, pageSize, params);
	}

	/**
	 * 获取网格基本配置
	 * 
	 * @param code
	 *            网格代码
	 * @param request
	 *            参数
	 * @return 配置信息
	 */
	@At("/config")
	@Ok("json")
	@GET
	public Object getGridConfig(String code, HttpServletRequest request) {
		ConfGrid model = gridHelper.getModel(code, 0);
		JSONObject json = new JSONObject();

		JSONObject base = new JSONObject();
		base.put("title", model.getCaption());
		/**
		 * 此处增加一个是否选择框返回字段，便于页面控制。 author yty 2017.3.14
		 */
		base.put("selectable", model.isSelectable());
		/** @Author:csj @Date: 是否高级搜索 */
		base.put("showFilter", model.isShowFilter());

		base.put("selectable", model.isSelectable());
		// base.put("directExport", model.isDirectExport());
		json.put("base", base);

		json.put("columns", gridHelper.extractModelColumns(request.getSession(), model));
		json.put("buttons", gridHelper.extractModelButtons(request.getSession(), model));
		json.put("filters", gridHelper.extractModelFilters(request.getSession(), model));
		// json.put("params",
		// gridHelper.extractModelParams(request.getSession(), model));

		return json;
	}

	/**
	 * 条件解析
	 * 
	 * @param req
	 *            请求
	 * @param model
	 *            模型
	 * @return 条件
	 */
	private Cnd extractFilters(HttpServletRequest req, ConfGrid model) {
		Cnd cnd = Cnd.where("1", "=", 1);
		if (model.isRightsControl()) {
			WebUser wu = UserManager.getCurrentUser();

//			cnd.and("tenant_id", "=", wu.getTenantId());
		}

		String filter = req.getParameter("condition");
		if (filter != null) {
			JSONArray filterArray = JSON.parseArray(filter);
			for (Object f : filterArray) {
				JSONObject item = (JSONObject) f;

				String op = item.getString("op");
				if (op == null) {
					continue;
				}

				if (IOperator.BETWEEN.equals(op)) {
					IOperator oprator = OperatorFactory.getOperator(op);

					JSONObject endItem = null;
					String toField = item.getString("field") + "_to";
					for (Object s : filterArray) {
						JSONObject sitem = (JSONObject) s;

						if (toField.equals(sitem.getString("field"))) {
							endItem = sitem;
						}
					}

					BetweenOperator betweenOperator = (BetweenOperator) oprator;
					if (endItem == null) {
						cnd.where().andBetween(item.getString("field"),
								betweenOperator.wrapperStartValue(item.getString("value"), item.getString("type")),
								betweenOperator.wrapperEndValue(null, item.getString("type")));
					} else {
						cnd.where().andBetween(item.getString("field"),
								betweenOperator.wrapperStartValue(item.getString("value"), item.getString("type")),
								betweenOperator.wrapperEndValue(endItem.getString("value"), item.getString("type")));
					}
				} else {
					IOperator oprator = OperatorFactory.getOperator(op);

					cnd.and(item.getString("field"), oprator.getOperator(),
							oprator.wrapperValue(item.getString("value"), item.getString("type")));
				}
			}
		}

		String where = req.getParameter("where");
		if (where != null) {
			JSONObject whereJson = JSON.parseObject(where);
			JSONArray rules = whereJson.getJSONArray("rules");

			for (Object rule : rules) {
				JSONObject item = (JSONObject) rule;
				IOperator oprator = OperatorFactory.getOperator(item.getString("op"));
				cnd.and(item.getString("field"), oprator.getOperator(),
						oprator.wrapperValue(item.getString("value"), item.getString("type")));
			}
		}

		String sortName = req.getParameter("sortname");

		System.out.println("\n\n\n\n:" + sortName);
		if (null != sortName) {
			cnd.orderBy(sortName, req.getParameter("sortorder"));
		}

		return cnd;
	}

	/**
	 * @param gridCode
	 *            网格code
	 * @param params
	 *            params
	 * @param values
	 *            字段名';'分隔
	 * @param request
	 *            request
	 * @param resp
	 *            resp
	 * @throws UnsupportedEncodingException
	 */
	@At("/data/expToXls")
	public void expToXls(@Param("gridCode") String gridCode, @Param("params") String params,
			@Param("values") String values, HttpServletRequest req, HttpServletResponse rep)
			throws UnsupportedEncodingException {
		// 设置为下载类型
		req.setCharacterEncoding("UTF-8");
		rep.setCharacterEncoding("UTF-8");
		rep.setHeader("Pragma", "no-cache");
		rep.setHeader("Cache-Control", "no-cache");
		rep.setDateHeader("Expires", 0);
		rep.setContentType("application/vnd.ms-excel;charset=UTF-8");

		WebUser wu = UserManager.getCurrentUser();

		if (wu == null) {
			throw new IllegalStateException("不能获取用户信息");
		} else {
			ConfGrid model = gridHelper.getModel(gridCode, 0);

			Cnd cnd = this.extractFilters(req, model);
			Map<String, Object> reqParams = ServletUtils.parametersToMap(req);

			rep.addHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode(model.getCaption(), "UTF-8") + "-"
							+ DateUtils.formatCurrentDate() + ".xlsx");
			exportService.expXls(cnd, model, values, reqParams, rep);
		}
	}

	/**
	 * 超级表单数据网格显示
	 * 
	 * @param formGuid
	 *            表单的Guid
	 * @return 超级表单配置信息
	 */
	@At("/superform/?")
	@Ok("fm:/grid_superform.html")
	public Map<String, Object> showSuperFormGrid(String formGuid) {
		Map<String, Object> rtn = new HashMap<>();
		rtn.put("guid", formGuid);

		return rtn;
	}

	/**
	 * 主子表网格获取子表数据
	 *
	 * @param table
	 *            主表
	 * @return 网格数据
	 */
	@At("/childData/?/?")
	@Ok("json")
	public JSONObject childData(String table, String mainGuid, HttpServletRequest request) {

		return gridHelper.getChildData(table, mainGuid);
	}

	/**
	 * test1
	 *
	 * @param grid
	 *            网格配置
	 * @return 页面
	 */
	@At("/exhibit/?")
	@Ok("fm:/grid_dms.html")
	public Map<String, Object> test1(String grid) {
		ConfGrid model = gridHelper.getModel(grid, 0);
		Map<String, Object> rtn = new HashMap<>();
		rtn.put("code", grid);
		rtn.put("useJsModel", model.getUseJsModule());

		return rtn;
	}

	/**
	 * test1
	 *
	 * @param grid
	 *            网格配置
	 * @return 页面
	 */
	@At("/dmsGrid2/?")
	@Ok("fm:/mcGrid.html")
	public Map<String, Object> dmsGrid2(String grid) {
		ConfGrid model = gridHelper.getModel(grid, 0);
		Map<String, Object> rtn = new HashMap<>();
		rtn.put("code", grid);
		rtn.put("useJsModel", model.getUseJsModule());

		return rtn;
	}

	/**
	 * 主子表格展示
	 *
	 * @param grid
	 *            网格配置
	 * @return 页面
	 */
	@At("/grid_with_sub/?")
	@Ok("fm:/grid_with_sub.html")
	public Map<String, Object> showGridWithSub(String grid, @Param("sub_grid") String subGrid) {
		ConfGrid model = gridHelper.getModel(grid, 0);
		Map<String, Object> rtn = new HashMap<>();
		rtn.put("code", grid);
		rtn.put("subGrid", subGrid);
		rtn.put("useJsModel", model.getUseJsModule());

		return rtn;
	}

	/**
	 * test2
	 *
	 * @param grid
	 *            网格配置
	 * @return 页面
	 */
	@At("/dmsTreeForm/?")
	@Ok("fm:/treeFormDms.html")
	public Map<String, Object> test2(String grid) {
		ConfGrid model = gridHelper.getModel(grid, 0);
		Map<String, Object> rtn = new HashMap<>();
		rtn.put("code", grid);
		rtn.put("useJsModel", model.getUseJsModule());

		return rtn;
	}
}
