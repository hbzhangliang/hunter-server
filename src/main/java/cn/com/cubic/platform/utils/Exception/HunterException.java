package cn.com.cubic.platform.utils.Exception;

import static org.slf4j.helpers.MessageFormatter.arrayFormat;

/**
 * @Author Meng.Liu
 * @Date 2017/12/8 15:56
 */
public class HunterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Long code;

    public HunterException() {
    }

    /**
     * @param code error code
     */
    public HunterException(Long code) {
        this.code = code;
    }

    /**
     * @param code error code
     * @param e exception
     */
    public HunterException(Long code, Throwable e) {
        super(e);
        this.code = code;
    }

    /**
     * @param code error code
     * @param message error message
     * @param args error message args
     */
    public HunterException(Long code, String message, Object ... args) {
        this(message, args);
        this.code = code;
    }

    /**
     * @param message error message
     * @param args error message args
     */
    public HunterException(String message, Object ... args) {
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
    public HunterException setCode(Long code) {
        this.code = code;
        return this;
    }


}
