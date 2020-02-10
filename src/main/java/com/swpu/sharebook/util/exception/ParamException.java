package com.swpu.sharebook.util.exception;

import com.swpu.sharebook.util.returnvalue.ResponseResult;

/**
 * 参数异常
 * @author Jack
 *
 */
public class ParamException extends JmSoftException {

	private static final long serialVersionUID = -1196851528373507875L;
	
	public ParamException(ResponseResult result){
		super(result);
	}
	
	public ParamException(String message){
		super(message);
	}
	
	public static void throwException(ResponseResult responseResult) throws ParamException{
		throw new ParamException(responseResult);
	}
}
