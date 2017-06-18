package link.smartwall.web.controller;

import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import link.smartwall.freemarker.FreemarkerViewMaker;
import link.smartwall.oss.mvc.OssViewMaker;

/**
 * 系统MVC加载界面
 * 
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @version 1.0
 * @since  2.0
 *        <p/>
 *        <p/>
 *        <p/>
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-8-13 lexloo
 *        </pre>
 */
@IocBy(type = ComboIocProvider.class, args = {"*link.smartwall.web.controller.SmartWallIocLoader",
                                              "*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
                                              "link.smartwall.web.controller",
                                              "link.smartwall.service"})
@Modules(packages = {"link.smartwall.web.controller"}, scanPackage = true)
@Views({OssViewMaker.class, FreemarkerViewMaker.class})
@Encoding(input = "UTF-8", output = "UTF-8")
public class CmsModule {}