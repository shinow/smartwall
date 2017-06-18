/**
 * 天恒众航(2013)
 */
package link.smartwall.basemodel;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础模型对象
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-1-9 lexloo
 *        </pre>
 */
public enum ObjectFactory {
    INSTANCE;

    private Map<String, Class<? extends AbstractObject>> objs = new HashMap<>();

    private ObjectFactory() {
        putObj("sfa_tenant", "com.itfsm.base.entity.Tenant");
        putObj("sfa_employee", "com.itfsm.base.entity.Employee");
        putObj("sfa_agency", "com.itfsm.web.sfa.base.entity.Agency");
        putObj("sfa_cusQualifi", "com.itfsm.web.sfa.base.entity.CusQualifi");
        putObj("sfa_channel", "com.itfsm.web.sfa.base.entity.Channel");
        putObj("sfa_dept", "com.itfsm.web.sfa.base.entity.Dept");
        putObj("sfa_dsrInfo", "com.itfsm.web.sfa.base.entity.DsrInfo");
        putObj("sfa_dsr_prop", "com.itfsm.web.sfa.base.entity.DsrProp");
        putObj("sfa_ka", "com.itfsm.web.sfa.base.entity.Ka");
        putObj("sfa_position", "com.itfsm.web.sfa.base.entity.Position");
        putObj("sfa_positionDept", "com.itfsm.web.sfa.base.entity.PositionDept");
        putObj("sfa_positionEmployee", "com.itfsm.web.sfa.base.entity.PositionEmployee");
        putObj("sfa_positionMobiFunc", "com.itfsm.web.sfa.base.entity.PositionMobiFunc");
        putObj("sfa_positionWebFunc", "com.itfsm.web.sfa.base.entity.PositionWebFunc");
        putObj("sfa_prop", "com.itfsm.web.sfa.base.entity.Prop");
        putObj("sfa_propCatalog", "com.itfsm.web.sfa.base.entity.PropCatalog");
        putObj("sfa_role", "com.itfsm.web.sfa.base.entity.Role");
        putObj("sfa_store", "com.itfsm.web.sfa.base.entity.Store");
        putObj("sfa_storeEmployee", "com.itfsm.web.sfa.base.entity.StoreEmployee");
        putObj("sfa_storeEmployeeLog", "com.itfsm.web.sfa.base.entity.StoreEmployeeLog");
        putObj("sfa_store_property", "com.itfsm.web.sfa.base.entity.StoreProperty");
        putObj("conf_func_workFlow", "com.itfsm.grid.entity.ConfFuncWorkFlow");
        putObj("conf_web_form", "com.itfsm.entity.ConfWebForm");
        putObj("conf_mobi_form", "com.itfsm.entity.ConfMobiForm");
        putObj("cfg_mobi_stat_widgets", "com.itfsm.config.entity.Widget");
        putObj("sfa_return_info", "com.itfsm.order.entity.ReturnInfo");
        putObj("sfa_order_info", "com.itfsm.order.entity.OrderInfo");
        putObj("sfa_sku_brand", "com.itfsm.order.entity.SKUBrand");
        putObj("sfa_sku_class", "com.itfsm.order.entity.SKUClass");
        putObj("sfa_sku_info", "com.itfsm.order.entity.SKUInfo");
        putObj("sfa_vendor", "com.itfsm.order.entity.Vendor");
        putObj("sfa_wareHouse_info", "com.itfsm.order.entity.WareHouseInfo");
        putObj("sfa_dataset_sql", "com.itfsm.grid.entity.DataSetSql");
        putObj("conf_single_page", "com.itfsm.grid.entity.ConfSinglePage");
        putObj("conf_modules", "com.itfsm.rights.entity.ConfModules");
        putObj("conf_modules_mobile", "com.itfsm.rights.entity.ConfModulesMobile");
        putObj("sfa_role", "com.itfsm.rights.entity.Role");
        putObj("conf_tree_data", "com.itfsm.base.entity.ConfTreeData");
        putObj("sfa_visit_step_conf", "com.itfsm.visit.entity.VisitStep");
        putObj("conf_grid", "com.itfsm.grid.entity.ConfGridEntity");
        putObj("conf_grid_col", "com.itfsm.grid.entity.ConfGridCol");
        putObj("conf_grid_action", "com.itfsm.grid.entity.ConfGridAction");
        putObj("conf_grid_filter", "com.itfsm.grid.entity.ConfGridFilter");
        putObj("em_project", "com.itfsm.center.entity.EmProject");
        putObj("em_problem", "com.itfsm.center.entity.EmProblem");
        putObj("em_daily_record", "com.itfsm.center.entity.EmDailyRecord");
        putObj("sfa_em_trade", "com.itfsm.center.entity.EmTrade");
        putObj("dms_dict_dt", "com.itfsm.dms.entity.DictDt");
        putObj("dms_vendors", "com.itfsm.dms.entity.Vendors");
        putObj("dms_items", "com.itfsm.dms.entity.Items");
        putObj("dms_region", "com.itfsm.dms.entity.Areamanage");
        putObj("dms_warehouse_type", "com.itfsm.dms.entity.WarehouseType");
        putObj("dms_warehouse", "com.itfsm.dms.entity.Warehouse");
        putObj("dms_price_Type", "com.itfsm.dms.entity.PriceSet");
        putObj("sfa_price_set_dt", "com.itfsm.order.entity.PriceSetDT");
        putObj("dms_customers", "com.itfsm.dms.entity.Customer");
        putObj("dms_warehouse_loc", "com.itfsm.dms.entity.WarehouseLoc");
        putObj("dms_sale_area", "com.itfsm.dms.entity.SaleArea");
        putObj("dms_item_producter", "com.itfsm.dms.entity.Producter");
        putObj("dms_item_brand", "com.itfsm.dms.entity.ItemBrand");
        putObj("dms_brand_class", "com.itfsm.dms.entity.BrandClass");
        putObj("exam_question_bank", "com.itfsm.web.entity.exam.QuestionBank");
        putObj("conf_form_visit", "com.itfsm.dms.entity.VisitManager");
        putObj("em_user", "com.itfsm.center.entity.EmUser");
        
    }

    @SuppressWarnings("unchecked")
    private void putObj(String name, String className) {
        try {
            objs.put(name, (Class<? extends AbstractObject>) Class.forName(className));
        } catch (Exception e) {

        }
    }

    public AbstractObject getObjectByCode(String code) {
        try {
            Class<? extends AbstractObject> clazz = this.objs.get(code);
            if (clazz == null) {
                return null;
            }
            AbstractObject ao = clazz.newInstance();
            ao.initValues();

            return ao;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("类不存在！代码：" + code);
        }
    }

    public AbstractObject getObjectNoGuid(String code) {
        try {
            Class<? extends AbstractObject> clazz = this.objs.get(code);
            if (clazz == null) {
                return null;
            }
            AbstractObject ao = clazz.newInstance();

            return ao;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException("类不存在！代码：" + code);
        }
    }

    public Class<?> getObjectClass(String code) {
        return this.objs.get(code);
    }
}
