package link.smartwall.grid.imp;
///**
// * 天恒众航（北京）科技股份公司(2014)
// */
//package com.itfsm.grid.imp;
//
//import java.util.List;
//
//import org.nutz.dao.Cnd;
//import org.nutz.dao.Dao;
//import org.nutz.dao.Sqls;
//import org.nutz.dao.entity.Record;
//import org.nutz.dao.sql.Sql;
//
//import com.dcits.basemodel.ObjectManager;
//import com.dcits.xsb.db.DbUtils;
//import com.itfsm.basemodel.AbstractObject;
//import com.itfsm.gis.LatLng;
//import com.itfsm.grid.entity.GridColumn;
//import com.itfsm.grid.entity.GridModel;
//import com.itfsm.kernel.Kernel;
//import com.itfsm.util.HttpUtils;
//
///**
// * 导入时逆地址解析
// * 
// * @version 1.0
// * @author <a herf="xiaojiaxingbj@163.com">xiaojiaxing</a>
// * @since 外勤管家3.0
// * 
// *        <pre>
// * 历史：
// *      建立: Oct 15, 2014 JiaXing
// * </pre>
// */
//public class ImpReverAddressResolution {
//
//    /**
//     * @param model 网格code
//     * @param tenantid 企业id
//     */
//    public void reverAddressResolution(String model, int tenantid) {
//        Dao dao = DbUtils.newDao();
//
//        GridModel gridModel = dao.fetch(GridModel.class, Cnd.where("code", "=", model));
//
//        if (gridModel.getBizModel() != null) {
//            ObjectManager bomgr = Kernel.getInstance().getObjectManager();
//            AbstractObject ao = bomgr.createObject(gridModel.getBizModel());
//
//            // 表名
//            String tableName = ao.entityTableName();
//            // 地址对应字段名
//            String address = getAddrFeild(dao, gridModel);
//
//            // 更新经纬度
//            if (address != null) {
//                updateLonLat(dao, address, tableName, tenantid);
//            }
//        }
//    }
//
//    /**
//     * 查询存放地址的表字段名
//     * 
//     * @param dao dao
//     * @param gridModel 网格类
//     * @return address
//     */
//    private String getAddrFeild(Dao dao, GridModel gridModel) {
//        String address = null;
//
//        // 获取字段配置
//        List<GridColumn> gridColumns = dao.query(GridColumn.class, Cnd.where("masterid", "=", gridModel.getId()));
//        for (GridColumn gc : gridColumns) {
//            if (gc.isReverAddr()) {
//                if (gc.getBizCode() != null) {
//                    address = gc.getBizCode();
//                } else {
//                    address = gc.getCode();
//                }
//            }
//        }
//
//        return address;
//    }
//
//    /**
//     * 根据地址更新经纬度
//     * 
//     * @param dao dao
//     * @param address 地址对应字段名
//     * @param tableName 表名
//     * @param tenantid tenantid
//     */
//    private void updateLonLat(Dao dao, String address, String tableName, int tenantid) {
//        // 查询出需要改的数据
//        String getSql =
//                "select id, $address from $tableName where $address is not null and tenantid = @tenantid and latitude<1 and longitude <1";
//
//        Sql querySql = Sqls.create(getSql);
//        querySql.vars().set("address", address).set("tableName", tableName);
//        querySql.params().set("tenantid", tenantid);
//        querySql.setCallback(Sqls.callback.records());
//
//        dao.execute(querySql);
//        @SuppressWarnings("unchecked")
//        List<Record> record = (List<Record>) querySql.getResult();
//
//        // 修改查询出的数据
//        String setSql = "update $tableName set latitude = @latitude, longitude =@longitude where id = @id";
//        Sql updateSql = Sqls.create(setSql);
//
//        boolean needExecute = false;
//        for (Record r : record) {
//            LatLng ll = HttpUtils.getLatLng(r.getString(address));
//
//            updateSql.vars().set("tableName", tableName);
//            updateSql.params().set("latitude", ll.getLat()).set("longitude", ll.getLng()).set("id", r.getInt("id"));
//            updateSql.addBatch();
//
//            needExecute = true;
//        }
//
//        if (needExecute) {
//            dao.execute(updateSql);
//        }
//    }
//}
