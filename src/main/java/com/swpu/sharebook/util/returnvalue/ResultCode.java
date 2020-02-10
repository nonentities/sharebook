package com.swpu.sharebook.util.returnvalue;

/**
 * 错误代码约束
 * @author liuJack
 *
 */
public enum ResultCode {

	/**
	 * 数据验证相关错误编码
	 */
	DataErrorCode(100),
	
	/**
	 * 系统错误相关编码
	 */
	SysErrorCode(500),
	
	/**
	 * 请求错误相关错误编码
	 */
	ReqErrorCode(400);
	
	private Integer code;
	
	private ResultCode(Integer code){
		this.code = code;
	}
	
	public Integer getCode(){
		return this.code;
	}
	
}
