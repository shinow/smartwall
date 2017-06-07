package link.smartwall.controls.webview.support;

import android.webkit.JavascriptInterface;

import link.smartwall.controls.webview.NativeWebView;

public class JSNativeClass {
    private NativeWebView webView;
    public JSNativeClass(NativeWebView webView) {
        this.webView = webView;
    }

    /**
     * 功能
     *
     * @param model_name 插件名称
     * @param method     插件方法7
     * @param param      参数
     */
    @JavascriptInterface
    public void invoke(String model_name, String method, String param) {
//        JSParam jsParam = JSON.parseObject(param, JSParam.class);
//        JSPlugin instance = manager.getPluginInstance(model_name);
//        if (jsParam != null) {
//            JSCallback callback = new JSCallback(this.webView, null);
//            callback.setSuccessId(jsParam.getCbSuccId());
//            callback.setErrId(jsParam.getCbErrId());
//            boolean execute = instance.execute(webView, method, jsParam.getOptions(), callback);
//            if (!execute) {
//                callback.err("未找到对应的方法");
//            }
//        }
    }
}