package link.smartwall.controls.webview.support;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * js插件管理器
 * Created by liupei on 2017/4/14.
 */
public class JSPluginManager {
    private HashMap<String, JSPluginInfo> pluginMap = new HashMap<>();
    private HashMap<String, JSEventInfo> eventMap = new HashMap<>();
    private Context context;

    public JSPluginManager(String configFile, Context context) {
        this.context = context;
        initJSPlugin(configFile);
    }

    /**
     * 初始化插件管理器
     */
    private void initJSPlugin(String configFile) {
        pluginMap.clear();
        try {
            //读取配置文件
            InputStreamReader inputReader = new InputStreamReader(context.getAssets().open(configFile));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            String result = "";
            while ((line = bufReader.readLine()) != null)
                result += line;

            //解析配置文件
            JSONObject dict = new JSONObject(result);
            if (dict.length() > 0) {
                //从updateUrl中获取核心文件的名字
                JSONArray plugins = dict.getJSONArray("plugins");
                if (plugins == null || plugins.length() == 0) return;
                for (int i = 0; i < plugins.length(); i++) {
                    JSONObject plugin = plugins.getJSONObject(i);
                    JSPluginInfo info = new JSPluginInfo();
                    info.pluginName = plugin.getString("pluginName");
                    info.pluginClass = plugin.getString("pluginClass");

                    //遍历接口信息
                    JSONArray exports = plugin.getJSONArray("exports");
                    if (exports != null && exports.length() > 0) {
                        for (int j = 0; j < exports.length(); j++) {
                            String showMethod = exports.getString(j);
                            if (showMethod != null) {
                                LDJSExportDetail tmp = new LDJSExportDetail(showMethod);
                                info.exports.put(showMethod, tmp);
                            }
                        }
                    }

//                    // 初始化插件实例
//                    JSPlugin pluginInstance = instantiatePlugin(info.pluginClass);
//                    if(pluginInstance != null && webView != null && activityInterface != null){
//                        pluginInstance.privateInitialize(activityInterface, webView);
//                        info.instance = pluginInstance;
//                    }

                    info.instance = instantiatePlugin(info.pluginClass);
                    pluginMap.put(info.pluginName, info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 获取监听对象
//     */
//    public JSEvent getEventInstance(String eventName){
//        JSEventInfo jsEventInfo = eventMap.get(eventName);
//        if(jsEventInfo!=null){
//            return jsEventInfo.getInstance();
//        }
//        return null;
//    }

    /**
     * 获取插件对象
     */
    public JSPlugin getPluginInstance(String pluginName) {
        JSPluginInfo jsPluginInfo = pluginMap.get(pluginName);
        if (jsPluginInfo != null) {
            return jsPluginInfo.getInstance();
        } else {
            return null;
        }
    }

    /**
     * 获取核心js
     */
    public String localCoreBridgeJSCode(String file) {
        String jsBrideCodeStr = "";
        try {
            System.out.println("file:" + file);
            InputStream instream = context.getAssets().open(file);
            if (instream != null) {
                StringBuilder content = new StringBuilder(); // 文件内容字符串
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                char[] buffer = new char[2048];
                int length;
                // 分行读取
                while ((length = buffreader.read(buffer)) > 0) {
                    content.append(buffer, 0, length);
                }
                jsBrideCodeStr = content.toString();
                instream.close();
            }
        } catch (java.io.FileNotFoundException e) {
            Log.d("TestFile", "The File doesn't not exist.");
        } catch (IOException e) {
            Log.d("TestFile", e.getMessage());
        }
        return jsBrideCodeStr;
    }

    /**
     * 根据插件对应的className生成一个插件实例
     */
    private JSPlugin instantiatePlugin(String className) {
        JSPlugin ret = null;
        try {
            Class<?> c = null;
            if ((className != null) && !("".equals(className))) {
                c = Class.forName(className);
            }
            if (c != null & JSPlugin.class.isAssignableFrom(c)) {
                ret = (JSPlugin) c.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding plugin " + className + ".");
        }
        return ret;
    }

    public HashMap<String, JSEventInfo> getEventMap() {
        return eventMap;
    }

    public HashMap<String, JSPluginInfo> getPluginMap() {
        return pluginMap;
    }

    public class LDJSExportDetail {
        public String showMethod; //JSAPI调用的action方法

        public LDJSExportDetail(String theShowMethod) {
            this.showMethod = theShowMethod;
        }
    }
}
