package com.swpu.sharebook.util.exception;


import com.swpu.sharebook.util.returnvalue.ResponseResult;

/**
 * 业务异常
 * @author Jack
 *
 */
public class BizException extends JmSoftException {

	private static final long serialVersionUID = -1196851528373507875L;
	
	public BizException(ResponseResult result){
		super(result);
	}
	
	public BizException(String message){
		super(message);
	}
	
	public static void throwException(ResponseResult responseResult) throws BizException {
		throw new BizException(responseResult);
	}

}
