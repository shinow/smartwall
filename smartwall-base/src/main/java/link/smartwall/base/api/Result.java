/**
 * SmartWall(2015~2016)
 */
package link.smartwall.base.api;

import com.alibaba.fastjson.JSONObject;

/**
 * 基础返回返回结果
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤管家5.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2016年6月06日 lexloo
 *        </pre>
 */
public class Result {
    /**
     * 成功代码
     */
    public static final int SUCCESS = 0;
    /**
     * 失败代码
     */
    public static final int FAILURE = 1;

    /**
     * 结果代码，0：正常， 1：出错
     */
    private int code;
    /**
     * 消息内容
     */
    private Object message;
    
    /**
     * 错误代码
     */
    private String errorCode;
    
    
    /**
     * 构造函数
     * 
     * @param code 成功或失败代码
     * @param message 成功或失败结果
     * @param errorCode 错误代码
     */
    public Result(int code, Object message, String errorCode) {
        this.code = code;
        this.message = message;
        this.errorCode = errorCode;
    }
    

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 成功消息
     * 
     * @return 消息
     */
    public static Result successResult() {
        return new Result(SUCCESS, null);
    }

    /**
     * 成功消息
     * 
     * @param message 消息体
     * @return 消息
     */
    public static Result successResult(Object message) {
        return new Result(SUCCESS, message);
    }

    /**
     * 失败消息
     * 
     * @param message 消息体
     * @return 消息
     */
    public static Result failureResult(String message) {
        return new Result(FAILURE, message);
    }

    /**
     * 失败消息
     * 
     * @param failureCode 失败代码
     * @param message 消息体
     * @return 消息
     */
    public static Result failureResult(int failureCode, String message) {
        return new Result(failureCode, message);
    }

    /**
     * 构造函数
     * 
     * @param code 代码
     * @param message 消息
     */
    public Result(int code, Object message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取代码
     * 
     * @return 代码
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取message
     * 
     * @return
     */
    public Object getMessage() {
        return this.message;
    }

    /**
     * 是否成功结果
     * 
     * @return 是否成功结果
     */
    public boolean isSuccess() {
        return this.code == Result.SUCCESS;
    }

    /**
     * 是否失败结果
     * 
     * @return 是否失败结果
     */
    public boolean isFailure() {
        return this.code == Result.FAILURE;
    }

    @Override
    public String toString() {
        return toJSONString();
    }

    /**
     * 
     * @return JSON result
     */
    public String toJSONString() {
        JSONObject result = new JSONObject();

        result.put("code", this.code);
        result.put("message", this.message);

        return result.toJSONString();
    }
}
