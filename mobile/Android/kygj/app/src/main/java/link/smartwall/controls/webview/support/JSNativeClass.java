package link.smartwall.controls.webview.support;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import link.smartwall.controls.activity.NvWebViewActivity;
import link.smartwall.controls.webview.NativeWebView;

import static android.R.attr.x;

public class JSNativeClass {
    private NativeWebView webView;


    /**
     * invoke
     *
     * @param params params
     */
    @JavascriptInterface
    public void invoke(String module, String funcName, String data) {
        System.out.println(data);
        JSONObject jsonData = JSON.parseObject(data);
        System.out.println(jsonData.getString("options"));
        if ("ui".equals(module)) {
            if ("goURL".equals(funcName)) {
                Context context = this.webView.getContext();
                Intent intent = new Intent(context, NvWebViewActivity.class);
                intent.putExtra("url", jsonData.getString("options"));

                context.startActivity(intent);
            } else if ("close".equals(funcName)) {
                this.webView.obtainActivity().finish();
            }
        }
    }

    @JavascriptInterface
    public void on(String func) {
        System.out.println(func);

    }

    public JSNativeClass(NativeWebView webView) {
        this.webView = webView;
    }
}