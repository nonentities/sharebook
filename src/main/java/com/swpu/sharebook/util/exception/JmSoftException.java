package com.swpu.sharebook.util.exception;


import com.swpu.sharebook.util.returnvalue.ResponseResult;

/**
 * 涓诲紓甯�
 * @author Jack
 *
 */
public class JmSoftException extends Exception {

	private static final long serialVersionUID = -1196851528373507875L;
	
	protected ResponseResult responseResult;
	
	public JmSoftException(ResponseResult result){
		super(result.getMsg());
		this.responseResult = result;
	}
	
	public JmSoftException(String message){

		super(message);
	}

	public ResponseResult getResponseResult() {
		return responseResult;
	}

}
