package cn.com.cubic.platform.utils.Exception;

import cn.com.cubic.platform.utils.resp.TerminalBaseResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常全局处理 如果返回的为json数据或其它对象，添加该注解
 */
@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	public GlobalExceptionHandler(){
		logger.info("Init global exception...");
	}

	// 添加全局异常处理流程，根据需要设置需要处理的异常
	@ExceptionHandler(value = IllegalArgumentException.class)
	public Object MethodArgumentNotValidHandler(HttpServletRequest request, IllegalArgumentException exception) {
		return TerminalBaseResponse.error(exception.getMessage());
	}

	@ExceptionHandler(value = Exception.class)
	public Object defaultErrorHandler(HttpServletRequest req, Exception e) {
		// 打印异常信息：
		for( Throwable throwable : ExceptionUtils.getThrowableList(e) ){
			logger.debug("Error : [{}]", throwable.getClass().getName());
			if( throwable instanceof TerminalException ){
				return TerminalBaseResponse.error(throwable.getMessage());
			}
		}
		logger.error("GlobalDefaultExceptionHandler.defaultErrorHandler", e);
		return TerminalBaseResponse.error("未知异常");
	}
}
