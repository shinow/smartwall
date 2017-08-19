package link.smartwall.controls.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import link.smartwall.controls.webview.NativeWebView;
import link.smartwall.kygj.R;

public class NvWebViewFragment extends Fragment {
    private NativeWebView m_webView;
    private String url;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Bundle bundle = getArguments();
        url = bundle.getString("url");
//        js = bundle.getString("js");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_web_views, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        m_webView = (NativeWebView) view.findViewById(R.id.nativewebview);

        init();
    }

    private void init() {
        m_webView.loadUrl(url);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        ActivityResultListener listenr = m_webView.getListenr();
//        if(listenr!=null){
//            listenr.onActivityResult(requestCode, resultCode, data);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        m_webView.onDestroy();

        super.onDestroy();
    }
}
