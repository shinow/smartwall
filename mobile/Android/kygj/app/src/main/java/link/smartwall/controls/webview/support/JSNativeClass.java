package link.smartwall.controls.webview.support;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import link.smartwall.controls.webview.NativeWebView;
import link.smartwall.controls.activity.NvWebViewActivity;

public class JSNativeClass {
    private NativeWebView webView;


    /**
     * open a new window
     *
     * @param action action
     * @param params params
     */
    @JavascriptInterface
    public void openWindow(String action, String params) {
        if (action.equals("ACTION_WEBVIEW")) {
            Context context = this.webView.getContext();
            Intent intent = new Intent(context, NvWebViewActivity.class);

            context.startActivity(intent);
        }
    }

    public JSNativeClass(NativeWebView webView) {
        this.webView = webView;
    }
}