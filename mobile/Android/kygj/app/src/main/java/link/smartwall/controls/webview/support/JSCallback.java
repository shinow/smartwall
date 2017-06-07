package link.smartwall.controls.webview.support;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebView;

import com.alibaba.fastjson.JSON;

/**
 * 回调
 * Created by liupei on 2017/4/14.
 */
public class JSCallback implements IJSCallback {
    private WebView webView;
    private Handler uiHandler;
    private String successId;
    private String errId;

    public JSCallback(WebView webView, Handler uiHandler) {
        this.webView = webView;
        this.uiHandler = uiHandler;
    }

    private void sendPluginResult(final JSPluginResult result) {
        final String datas = JSON.toJSONString(result);
        if (webView == null) {
            return;
        }
        if (uiHandler == null) {
            uiHandler = new Handler(Looper.getMainLooper());
        }
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                String url = "javascript:iTek.__html5_cb(%s);";
                String urls = String.format(url,datas );
                Log.i("jscallbacl", urls);
                webView.loadUrl(urls);
            }
        });
    }

    @Override
    public void success(Object message) {
        JSPluginResult pluginResult = new JSPluginResult();
        pluginResult.setBody(message);
        pluginResult.setStatus("0");
        pluginResult.setCbSuccId(successId);
        pluginResult.setCbErrId(errId);
        sendPluginResult(pluginResult);
    }

    @Override
    public void err(Object message) {
        JSPluginResult pluginResult = new JSPluginResult();
        pluginResult.setBody(message);
        pluginResult.setStatus("1");
        pluginResult.setCbSuccId(successId);
        pluginResult.setCbErrId(errId);
        sendPluginResult(pluginResult);
    }

    public void setSuccessId(String successId) {
        this.successId = successId;
    }

    public void setErrId(String errId) {
        this.errId = errId;
    }
}
