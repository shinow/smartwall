/**
 * 天恒众航（北京）科技股份公司(2014)
 */
package link.smartwall.util;

/**
 * 地址
 * 
 * @version 1.0
 * @author <a herf="xiaojiaxingbj@163.com">xiaojiaxing</a>
 * @since 外勤管家3.0
 * 
 *        <pre>
 * 历史：
 *      建立: Oct 15, 2014 JiaXing
 * </pre>
 */
public class Address {
    /**
     * 地址
     */
    private String addr;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;
    /**
     * 县
     */
    private String district;

    /**
     * @return the addr
     */
    public String getAddr() {
        return addr;
    }

    /**
     * @param addr the addr to set
     */
    public void setAddr(String addr) {
        this.addr = addr;
    }

    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * @param district the district to set
     */
    public void setDistrict(String district) {
        this.district = district;
    }

}
