package link.smartwall.controls.webview.support;

/**
 * JS返回结果
 * Created by liupei on 2017/4/14.
 */

public class JSPluginResult {
    private String status;
    private Object body;
    private String cbSuccId;
    private String cbErrId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
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
