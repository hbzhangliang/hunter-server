package cn.com.cubic.platform.utils.Exception;

import static org.slf4j.helpers.MessageFormatter.arrayFormat;

/**
 * @Author Meng.Liu
 * @Date 2017/12/8 15:56
 */
public class TerminalException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Long code;

    public TerminalException() {
    }

    /**
     * @param code error code
     */
    public TerminalException(Long code) {
        this.code = code;
    }

    /**
     * @param code error code
     * @param e exception
     */
    public TerminalException(Long code, Throwable e) {
        super(e);
        this.code = code;
    }

    /**
     * @param code error code
     * @param message error message
     * @param args error message args
     */
    public TerminalException(Long code, String message, Object ... args) {
        this(message, args);
        this.code = code;
    }

    /**
     * @param message error message
     * @param args error message args
     */
    public TerminalException(String message, Object ... args) {
        super(arrayFormat(message, args).getMessage(),
                arrayFormat(null, args).getThrowable());
    }

    /**
     * @return the code
     */
    public Long getCode() {
        return code;
    }

    /**
     * @param code the code
     * @return this
     */
    public TerminalException setCode(Long code) {
        this.code = code;
        return this;
    }


}
