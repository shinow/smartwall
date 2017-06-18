package link.smartwall.web.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.entity.Record;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import link.smartwall.base.api.Result;
import link.smartwall.service.DataService;
import link.smartwall.service.TreeService;
import link.smartwall.util.StrUtils;

@IocBean
@At("/data")
public class DataController {

    @Inject
    private DataService dataService;
    @Inject
    private TreeService treeService;

    @At("/select/getInfo")
    @Ok("json")
    public Result querySelectInfo(@Param("code") String code) {
        try {
            return Result.successResult(dataService.getSelectContent(code));
        } catch (Exception e) {
            return Result.failureResult(e.getMessage());
        }
    }
    
    @At("/tree")
    @Ok("json")
    public List<Record> queryTree(@Param("treeId") String treeId,HttpServletRequest request) {
        try {
            return treeService.queryTree(treeId,getFieldParameters(request));
        } catch (Exception e) {
            return null;
        }
    }
    private Map<String, String> getFieldParameters(HttpServletRequest req) {
        Map<String, String> result = new HashMap<String, String>();

        @SuppressWarnings({"rawtypes"})
        Enumeration<?> em = req.getParameterNames();
        while (em.hasMoreElements()) {
            String paramName = StrUtils.c2str(em.nextElement(), "u");

            result.put(paramName.toLowerCase(), req.getParameter(paramName));
            //if (paramName.startsWith("p_")) {
           //     result.put(paramName.substring(2).toLowerCase(), req.getParameter(paramName));
            //}
        }

        return result;
    }

}
