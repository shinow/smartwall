package link.smartwall.controls.webview.support;

import android.webkit.JavascriptInterface;

import link.smartwall.controls.webview.NativeWebView;

public class JSNativeCamera {
    private NativeWebView webView;

    /**
     * 返回IMEI
     * @return IMEI
     */
    @JavascriptInterface
    public String imei() {
        return "imei";
    }

    /**
     * 拨打电话
     */
    @JavascriptInterface
    public void dial() {

    }

    public JSNativeCamera(NativeWebView webView) {
        this.webView = webView;
    }
}