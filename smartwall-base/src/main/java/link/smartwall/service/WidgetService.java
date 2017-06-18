package link.smartwall.service;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;

import link.smartwall.base.api.Result;

/**
 * Created by caisj on 2017/4/19.
 */
@IocBean(args = { "refer:dao" })
public class WidgetService extends BaseService {

    public WidgetService(Dao dao) {
        super(dao);
    }


    /**
     * 获取主管版信息
     * @return
     */
    public Result getFormData (){

        return Result.successResult();
    }
}
