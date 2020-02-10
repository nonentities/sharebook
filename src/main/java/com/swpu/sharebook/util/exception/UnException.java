package com.swpu.sharebook.util.exception;
import com.swpu.sharebook.util.returnvalue.ResponseResult;

/**
 * 未知异常
 * @author Jack
 *
 */
public class UnException extends JmSoftException {

	private static final long serialVersionUID = -1196851528373507875L;

	public UnException(ResponseResult result){
		super(result);
	}
	
	public UnException(String message){
		super(message);
	}
	public static void throwException(ResponseResult responseResult) throws UnException{
		throw new UnException(responseResult);
	}

}
