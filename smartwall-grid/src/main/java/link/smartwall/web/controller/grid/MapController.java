package link.smartwall.web.controller.grid;
///**
// * SmartWall(2014)
// */
//package com.itfsm.web.controller.grid;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.nutz.ioc.loader.annotation.IocBean;
//import org.nutz.mvc.annotation.At;
//import org.nutz.mvc.annotation.Ok;
//import org.nutz.mvc.annotation.Param;
//
///**
// * 地图相关控制器
// */
//@At("/map/leaflet")
//@IocBean
//public class MapController {
//    /**
//     * test2
//     *
//     * @param grid 网格配置
//     * @return 页面
//     */
//    @At("/select_lng_lat")
//    @Ok("fm:/map/leaflet/select_lng_lat.html")
//    public Map<String, Object> selectLngLat(@Param("lng") double lng, @Param("lat") double lat) {
//        Map<String, Object> rtn = new HashMap<>();
//        rtn.put("lng", lng);
//        rtn.put("lat", lat);
//
//        return rtn;
//    }
//}
