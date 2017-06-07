package link.smartwall.controls.webview.support;

/**
 * js请求参数类
 * Created by liupei on 2017/4/14.
 */

public class JSParam {
    //参数
    private String options;
    //请求成功回调
    private String cbSuccId;
    //请求失败回调
    private String cbErrId;

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getCbSuccId() {
        return cbSuccId;
    }

    public void setCbSuccId(String cbSuccId) {
        this.cbSuccId = cbSuccId;
    }

    public String getCbErrId() {
        return cbErrId;
    }

    public void setCbErrId(String cbErrId) {
        this.cbErrId = cbErrId;
    }
}
