package com.swpu.sharebook.interceptor;
import javax.servlet.http.HttpServletRequest;

import com.swpu.sharebook.util.exception.JmSoftException;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import com.swpu.sharebook.util.returnvalue.ResultDic;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionHandle {
	@ExceptionHandler(Exception.class)
	public ResponseResult exceptionAction(Exception exception, HttpServletRequest request)  {
		System.err.println(exception.getMessage());
		Throwable throwable = exception.getCause() == null ? exception : findException(exception.getCause());
		if(throwable instanceof JmSoftException) {
			return ((JmSoftException)throwable).getResponseResult();
		}	
		if(throwable instanceof RuntimeException) {
			return ResponseResult.ERROR(ResultDic.SYS_ERROR);
		}	
		return ResponseResult.ERROR(ResultDic.NON_ERROR);
	}

	private Throwable findException(Throwable cause) {
		if(cause.getCause() == null) return cause;
		if(cause instanceof JmSoftException) 
			return cause;
		return findException(cause.getCause());
	}
	/*@ExceptionHandler(value = BindException.class)
	public ResponseResult bindExceptionErrorHandler(BindException ex) throws Exception {
		BindingResult bindingResult=ex.getBindingResult();
		return null;
	}*/
}
