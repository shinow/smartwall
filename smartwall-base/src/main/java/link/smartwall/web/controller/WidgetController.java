package link.smartwall.web.controller;

import link.smartwall.base.api.Result;
import link.smartwall.service.WidgetService;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * Created by caisj on 2017/4/18.
 */
@At("/widget")
@IocBean
public class WidgetController {

    @Inject
    private WidgetService widgetService;

    @At("getFormData")
    @Ok("json")
    public Result getFormData (int tenantId){

        return widgetService.getFormData();
    }
}
