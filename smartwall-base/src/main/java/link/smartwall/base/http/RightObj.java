/**
 * 北京中航嘉城科技股份有限公司(2013) 
 */
package link.smartwall.base.http;

import java.util.HashMap;
import java.util.Map;

import link.smartwall.base.http.parameter.Parameters;
import link.smartwall.util.StrUtils;

/**
 * 权限对象.
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 销售宝 2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-3-8 lexloo
 * </pre>
 */
public class RightObj {
    /**
     * 服务代码
     */
    private String service;
    /**
     * 权限控制参数Map,里面只存小写值
     */
    private Map<String, String> parameters = new HashMap<String, String>();

    /**
     * 构造函数， 类似 p1=a&p2=b的结构
     * 
     * @param value 值
     */
    public RightObj(String value) {
        if (StrUtils.isEmpty(value)) {
            return;
        }

        String[] va = value.split(":");
        if (va.length > 1) {
            this.service = va[0].trim();

            String[] v = va[1].split("&");
            for (String s : v) {
                String[] c = s.toLowerCase().split("=");
                if (c.length > 1) {
                    parameters.put(c[0].trim(), c[1].trim());
                }
            }
        }
    }

    /**
     * 构造函数
     * 
     * @param service 服务代码
     * @param paramItems 参数项， ","隔开
     * @param params 参数
     */
    public RightObj(String service, String paramItems, Parameters params) {
        if (StrUtils.isEmpty(paramItems)) {
            return;
        }

        this.service = service;
        String[] v = paramItems.toLowerCase().split(",");
        for (String s : v) {
            String key = s.toLowerCase();
            parameters.put(key, params.getValueAsString(key));
        }
    }

    /**
     * @param service the service to set
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * @return the service
     */
    public String getService() {
        return service;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
        result = prime * result + ((service == null) ? 0 : service.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RightObj other = (RightObj) obj;
        if (parameters == null) {
            if (other.parameters != null) {
                return false;
            }
        } else if (!parameters.equals(other.parameters)) {
            return false;
        }
        if (service == null) {
            if (other.service != null) {
                return false;
            }
        } else if (!service.equals(other.service)) {
            return false;
        }
        return true;
    }
}
