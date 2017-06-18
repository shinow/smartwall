/**
 * SmartWall(2014)
 */
package link.smartwall.exception;

/**
 * 核心异常
 * 
 * @version 1.0
 * @author <a herf="lexloo@gmail.com">lexloo</a>
 * @since 外勤管家3.0
 * 
 *        <pre>
 * 历史：
 *      建立: 2014-5-9 lexloo
 * </pre>
 */
public class SfaBaseException extends RuntimeException {

    /**
     * UID
     */
    private static final long serialVersionUID = 2953336547366110133L;

    /**
     * 核心异常
     *
     * @param message 异常消息
     */
    public SfaBaseException(String message) {
        super(message);
    }
}
