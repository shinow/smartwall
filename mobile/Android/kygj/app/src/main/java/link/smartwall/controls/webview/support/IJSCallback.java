package link.smartwall.controls.webview.support;

/**
 * js回调响应接口
 */
public interface IJSCallback {
    void success(Object message);

    void err(Object message);
}
