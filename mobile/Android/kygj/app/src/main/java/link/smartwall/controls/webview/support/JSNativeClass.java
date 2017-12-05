package link.smartwall.controls.webview.support;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import link.smartwall.controls.activity.NvWebViewActivity;
import link.smartwall.controls.webview.NativeWebView;
import link.smartwall.kygj.questionbank.activity.DoQuestionActivity;
import link.smartwall.kygj.questionbank.domain.ChapterQuestionDo;
import link.smartwall.kygj.questionbank.http.LocalDataReader;

import static link.smartwall.kygj.questionbank.activity.DoQuestionActivity.questions;

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
    public String getChapterQuestion(String chapterGuid) {
        JSONArray data = new JSONArray();
        Map<String, Integer> rs = new HashMap<String, Integer>();
        List<ChapterQuestionDo> rd = LocalDataReader.readChapterQuestionDo(chapterGuid);
        for (ChapterQuestionDo chapterQuestionDo : rd) {
            rs.put(chapterQuestionDo.getQuestionGuid(), chapterQuestionDo.getResult());
        }

        JSONArray questions = LocalDataReader.readQuestions(chapterGuid);
        for (int i = 0, size = questions.size(); i < size; i++) {
            JSONObject jo = questions.getJSONObject(i);

            JSONObject item = new JSONObject();
            item.put("no", i + 1);
            item.put("guid", jo.getString("guid"));
            item.put("status", rs.get(jo.getString("guid")));

            data.add(item);
        }

        return data.toJSONString();
    }

    @JavascriptInterface
    public void startDoQuestion(int index, String subjectName, String chapterGuid, String chapterName) {
        Context context = this.webView.getContext();
        Intent startIntent = new Intent(context, DoQuestionActivity.class);
        Bundle argBundle = new Bundle();

        argBundle.putString("subjectName", subjectName);
        argBundle.putString("chapterName", chapterName);
        argBundle.putString("chapterGuid", chapterGuid);
        argBundle.putInt("index", index);

        startIntent.putExtras(argBundle);
        context.startActivity(startIntent);
    }

    @JavascriptInterface
    public String getDoQuestion(int no) {
        return JSON.toJSONString(questions.get(no - 1));
    }
}