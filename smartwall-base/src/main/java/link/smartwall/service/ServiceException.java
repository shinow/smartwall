/**
 * 天恒众航（北京）科技股份公司(2015)
 */
package link.smartwall.service;

import link.smartwall.base.api.Result;

/**
 * 服务层异常接口
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤助手 4.0.0
 * 
 *        <pre>
 * 历史：
 *      2015年2月27日 lexloo * 建立
 * </pre>
 */
public class ServiceException extends RuntimeException {
    /**
     * UID
     */
    private static final long serialVersionUID = -5974423939254665253L;

    /**
     * 
     * @param errorCode 错误码
     * @return Result
     */
    public Result getExceptionResult() {
        return new Result(Result.FAILURE, this.getMessage(), this.errCode);

    }
    /**
     * 
     */
    private String errCode;

    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @param errCode the errCode to set
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    /**
     * constructor
     * 
     * @param message 消息内容
     */
    public ServiceException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    /**
     * constructor
     * 
     * @param message 消息内容
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * 
     * constructor
     * 
     * @param message 消息内容
     * @param cause cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
