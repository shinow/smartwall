package link.smartwall.controls.webview.support;


import java.util.HashMap;

/**
 * Created by liupei on 2017/4/14.
 */
public class JSPluginInfo {
    public String pluginName; //插件名
    public String pluginClass; //插件对应的实现class
    public JSPlugin instance;
    public HashMap<String, JSPluginManager.LDJSExportDetail> exports = new HashMap<>(); //插件对外开放的接口配置

    public JSPlugin getInstance() {
        return instance;
    }

    public void setInstance(JSPlugin instance) {
        this.instance = instance;
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getPluginClass() {
        return pluginClass;
    }

    public void setPluginClass(String pluginClass) {
        this.pluginClass = pluginClass;
    }

    public HashMap<String, JSPluginManager.LDJSExportDetail> getExports() {
        return exports;
    }

    public void setExports(HashMap<String, JSPluginManager.LDJSExportDetail> exports) {
        this.exports = exports;
    }
}
