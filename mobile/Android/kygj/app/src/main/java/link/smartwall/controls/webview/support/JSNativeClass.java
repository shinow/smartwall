package link.smartwall.controls.webview.support;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import link.smartwall.controls.activity.NvWebViewActivity;
import link.smartwall.controls.webview.NativeWebView;
import link.smartwall.kygj.questionbank.activity.DoQuestionActivity;
import link.smartwall.kygj.questionbank.http.LocalDataReader;

public class JSNativeClass {
    private NativeWebView webView;


    /**
     * invoke
     *
     * @param module module
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

    @JavascriptInterface
    public int getChapterQuestionLength(String chapterGuid) {
        return LocalDataReader.readQuestionsLength(chapterGuid);
    }

    @JavascriptInterface
    public void startDoQuestion(String subjectName, String chapterGuid, String chapterName) {
        Context context = this.webView.getContext();
        Intent startIntent = new Intent(context, DoQuestionActivity.class);
        Bundle argBundle = new Bundle();

        argBundle.putString("subjectName", subjectName);
        argBundle.putString("chapterName", chapterName);
        argBundle.putString("chapterGuid", chapterGuid);

        startIntent.putExtras(argBundle);
        context.startActivity(startIntent);
    }
}