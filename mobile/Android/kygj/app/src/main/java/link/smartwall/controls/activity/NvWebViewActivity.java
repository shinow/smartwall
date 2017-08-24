package link.smartwall.controls.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import link.smartwall.controls.webview.NativeWebView;
import link.smartwall.kygj.R;

public class NvWebViewActivity extends FragmentActivity {
    private NativeWebView m_webView;
    private String url;
//    private boolean isNative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_views);
        m_webView = (NativeWebView) findViewById(R.id.nativewebview);

        url = getIntent().getStringExtra("url");
        initActivity();
    }

    private void initActivity() {
        m_webView.loadUrl(url);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (m_webView.getListenr() != null) {
//            m_webView.getListenr().onActivityResult(requestCode, resultCode, data);
//        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        m_webView.onDestroy();
        super.onDestroy();
    }
}
