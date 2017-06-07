package link.smartwall.controls.webview.support;

import link.smartwall.controls.webview.INatvieWebViewAware;


/***
 * 自定义开发插件的基类，所以自定义的插件必须继承该类，并实现该类的方法
 * @author panghui
 *
 */
public interface JSPlugin {
    /**
     * 根据URLcommand执行插件方法
     * <p>
     * 如果在Webview线程中调用：
     * activityInterface.getThreadPool().execute(runnable);
     * <p>
     * 如果需要在UI线程中执行：
     * activityInterface.getActivity().runOnUiThread(runnable);
     *
     * @return Whether the action was valid.
     */
    boolean execute(INatvieWebViewAware context, String realMethod, String param, IJSCallback callback);
}
