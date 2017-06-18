/**
 * SmartWall(2013)
 */
package link.smartwall.grid.imp;

import com.alibaba.fastjson.JSONArray;

/**
 * 数据导入对象
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since  2.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2013-4-12 lexloo
 * </pre>
 */
public class ImpDataObj {
    /**
     * 存储模型
     */
    private String model;
    /**
     * 需要导入的文件名
     */
    private String fileName;
    /**
     * 存储字段
     */
    private JSONArray fields = new JSONArray();
    /**
     * 是否使用快速导入，使用快速导入，不进行关联比较
     */
    private boolean useFastImp;

    /**
     * 企业Id
     */
    private int tenantId;
    /**
     * 企业code
     */
    private String useTenantCode;
    /**
     * 返回详细错误信息
     */
    private boolean returnDetail = false;
    /**
     * 存储数据
     */
    private JSONArray data = new JSONArray();

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the fields
     */
    public JSONArray getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(JSONArray fields) {
        this.fields = fields;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the useFastImp
     */
    public boolean isUseFastImp() {
        return useFastImp;
    }

    /**
     * @param useFastImp the useFastImp to set
     */
    public void setUseFastImp(boolean useFastImp) {
        this.useFastImp = useFastImp;
    }

    /**
     * @return the data
     */
    public JSONArray getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(JSONArray data) {
        this.data = data;
    }

    /**
     * @return the tenantId
     */
    public int getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId the tenantId to set
     */
    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @return the useTenantCode
     */
    public String getUseTenantCode() {
        if (useTenantCode == null || useTenantCode.length() == 0) {
            return "NORMAL";
        }

        return useTenantCode;
    }

    /**
     * @param useTenantCode the useTenantCode to set
     */
    public void setUseTenantCode(String useTenantCode) {
        this.useTenantCode = useTenantCode;
    }

    /**
     * @return the returnDetail
     */
    public boolean isReturnDetail() {
        return returnDetail;
    }

    /**
     * @param returnDetail the returnDetail to set
     */
    public void setReturnDetail(boolean returnDetail) {
        this.returnDetail = returnDetail;
    }

}
