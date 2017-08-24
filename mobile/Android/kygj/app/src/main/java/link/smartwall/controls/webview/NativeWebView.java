package link.smartwall.controls.webview;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import link.smartwall.controls.webview.support.JSNativeClass;


public class NativeWebView extends WebView implements INatvieWebViewAware {
    private Context context;
    private Handler handler = new Handler(Looper.getMainLooper());
//    private JSPluginManager manager;

    public NativeWebView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public NativeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public NativeWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
//        manager = new JSPluginManager("config/PluginConfig.json", this.getContext());

        WebSettings ws = this.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setAllowFileAccess(true);
        ws.setSupportZoom(false);
        ws.setUseWideViewPort(true);
        ws.setBuiltInZoomControls(false);
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setTextZoom(100);
        //设置定位的数据库路径
//		String dir = MyApplication.app.getContext().getDir("database", Context.MODE_PRIVATE).getPath();
//		ws.setGeolocationDatabasePath(dir);
        //启用地理定位
        ws.setGeolocationEnabled(true);
        ws.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        //  开启 DOM storage API 功能  
        ws.setDomStorageEnabled(true);
        //开启 database storage API 功能
        ws.setDatabaseEnabled(true);
        //开启 Application Caches 功能
        ws.setAppCacheEnabled(true);

        // Add Javascript support
        this.addJavascriptInterface(new JSNativeClass(this), "__Native__");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.setWebContentsDebuggingEnabled(true);
        }
        this.setWebChromeClient(new DefaultWebChromeClient(this.getContext()));

        this.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                onWebPageFinished();
                regNativie();
                readyWithEventName();
            }

        });
    }

    /**
     * 注册核心JS
     */
    private void onWebPageFinished() {
//        String coreBridgeJsCodeStr = manager.localCoreBridgeJSCode("sdk/html5_sdk_m.js");
//        // 多行注释
//        String multiComment = "/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/";
//        // 单行注释
//        String singleComment = "//[^\r\n]*+";
//        Pattern p = Pattern.compile(multiComment + "|" + singleComment + "|" + "\t|\r|\n");
//        Matcher m = p.matcher(coreBridgeJsCodeStr);
//        coreBridgeJsCodeStr = m.replaceAll("");
//        coreBridgeJsCodeStr = "javascript:" + coreBridgeJsCodeStr;
//        this.loadUrl(coreBridgeJsCodeStr);
    }

    private void regNativie() {
//        HashMap<String, JSPluginInfo> pluginMap = manager.getPluginMap();
//        Set<Map.Entry<String, JSPluginInfo>> entries = pluginMap.entrySet();
//        StringBuilder sb = new StringBuilder();
//        sb.append("javascript:");
//        for (Map.Entry<String, JSPluginInfo> info : entries) {
//            String key = info.getKey();
//            JSPluginInfo value = info.getValue();
//            HashMap<String, JSPluginManager.LDJSExportDetail> exports = value.getExports();
//            Set<String> strings = exports.keySet();
//            StringBuilder methods = new StringBuilder();
//            for (String string : strings) {
//                methods.append(string);
//                methods.append(",");
//            }
//            String path = "iTek.__html5_reg('" + key + "','" + methods.toString() + "');";
//            sb.append(path);
//        }
//        String regMessage = sb.toString();
//        Log.d("JSService", regMessage);
//        this.loadUrl(regMessage);
    }

    /**
     * 通过回调通知前端页面本地Service已经初始化完毕
     */
    private void readyWithEventName() {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                String url = "javascript:iTek.__html5_evt('JSBridgeReady');";
//                loadUrl(url);
//            }
//        });
    }

    @Override
    public Activity obtainActivity() {
        return (Activity) context;
    }

    @Override
    public NativeWebView obtainNvWebView() {
        return this;
    }

    public void onDestroy() {
    }
}
