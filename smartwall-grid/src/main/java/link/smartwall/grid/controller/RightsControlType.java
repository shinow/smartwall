package link.smartwall.grid.controller;
///**
// * 天恒众航(2014)
// */
//package com.itfsm.grid.controller;
//
//import java.util.Map;
//
//import com.itfsm.grid.controller.rights.IMobileRightsController;
//import com.itfsm.grid.controller.rights.IRightsController;
//import com.itfsm.grid.controller.rights.WithoutAuthorizationController;
//import com.itfsm.grid.controller.rights.WithoutAuthorizationMobileController;
//
///**
// * 权限控制类型
// * 
// * @version 1.0
// * @author <a herf="lexloo@gmail.com">lexloo</a>
// * @since 外勤管家3.0
// * 
// *        <pre>
// * 历史：
// *      建立: 2014-9-17 lexloo
// * </pre>
// */
//public enums RightsControlType {
//    /**
//     * 部门授权
//     */
//    DEPARTMENT_AUTHORIZATION {
//        @Override
//        public IRightsController createController(Map<String, Object> paramMap, ColumnSortInfo sortInfo) {
//            return new DepartmentAuthorizationController(paramMap, sortInfo);
//        }
//
//        @Override
//        public IMobileRightsController createMobileController(Map<String, Object> paramMap) {
//            return null;
//        }
//    },
//    /**
//     * 本部门
//     */
//    DEPARTMENT_WITH_CURR_LEVEL {
//        @Override
//        public IRightsController createController(Map<String, Object> paramMap, ColumnSortInfo sortInfo) {
//            return new DepartmentWithCurrLevelController(paramMap, sortInfo);
//        }
//
//        @Override
//        public IMobileRightsController createMobileController(Map<String, Object> paramMap) {
//            return null;
//        }
//    },
//    /**
//     * 部门包括下级部门
//     */
//    DEPARTMENT_WITH_LOW_LEVEL {
//        @Override
//        public IRightsController createController(Map<String, Object> paramMap, ColumnSortInfo sortInfo) {
//            return new DepartmentWithLowLevelController(paramMap, sortInfo);
//        }
//
//        @Override
//        public IMobileRightsController createMobileController(Map<String, Object> paramMap) {
//            return new DepartmentWithLowLevelMobileController(paramMap);
//        }
//    },
//    /**
//     * 部门包括上级部门
//     */
//    DEPARTMENT_WITH_UP_LEVEL {
//        @Override
//        public IRightsController createController(Map<String, Object> paramMap, ColumnSortInfo sortInfo) {
//            return null;
//        }
//
//        @Override
//        public IMobileRightsController createMobileController(Map<String, Object> paramMap) {
//            return new DepartmentWithUpLevelMobileController(paramMap);
//        }
//    },
//
//    /**
//     * 不需要权限控制
//     */
//    WITHOUT_AUTHORIZATION {
//
//        @Override
//        public IRightsController createController(Map<String, Object> paramMap, ColumnSortInfo sortInfo) {
//            return new WithoutAuthorizationController(paramMap, sortInfo);
//        }
//
//        @Override
//        public IMobileRightsController createMobileController(Map<String, Object> paramMap) {
//            return new WithoutAuthorizationMobileController(paramMap);
//        }
//
//    },
//    /**
//     * 经销商授权
//     */
//    AGENCY_AUTHORIZATION {
//        @Override
//        public IRightsController createController(Map<String, Object> paramMap, ColumnSortInfo sortInfo) {
//            return new AgencyAuthorizationController(paramMap, sortInfo);
//        }
//
//        @Override
//        public IMobileRightsController createMobileController(Map<String, Object> paramMap) {
//            return null;
//        }
//    },
//    /**
//     * 经销商控制（网格数据源要有agecnyid广燕）
//     */
//    EMP_AGENCY_AUTHORIZATION {
//        @Override
//        public IRightsController createController(Map<String, Object> paramMap, ColumnSortInfo sortInfo) {
//            return new EmpAgencyAuthorizationController(paramMap, sortInfo);
//        }
//
//        @Override
//        public IMobileRightsController createMobileController(Map<String, Object> paramMap) {
//            return null;
//        }
//    },
//    /**
//     * 广燕数据权限控制
//     */
//    GY_AUTHORIZATION {
//        @Override
//        public IRightsController createController(Map<String, Object> paramMap, ColumnSortInfo sortInfo) {
//            return new GyAuthorizationController(paramMap, sortInfo);
//        }
//
//        @Override
//        public IMobileRightsController createMobileController(Map<String, Object> paramMap) {
//            return null;
//        }
//    };
//
//    /**
//     * 创建控制器
//     * 
//     * @param paramMap 控制参数
//     * @param sortInfo 排序参数
//     * @return 控制器
//     */
//    public abstract IRightsController createController(Map<String, Object> paramMap, ColumnSortInfo sortInfo);
//
//    /**
//     * 创建手机端权限控制器
//     * 
//     * @param paramMap 控制参数
//     * @return 手机权限控制器
//     */
//    public abstract IMobileRightsController createMobileController(Map<String, Object> paramMap);
//
//}
