package com.swpu.sharebook.util.returnvalue;

/**
 * 错误代码字典
 * @author liuJack
 *
 */
public enum ResultDic {

	/**请求成功*/
	SUCCESS(0,"操作成功",true),

	/**数据传输有误*/
	DATA_WRONG(100,"数据传输有误",false),
	
	/**未登录*/
	NOT_LOGIN(1,"登录已过期",false),
	
	/**登录超时*/
	LOGIN_TIME_OUT(2,"登录超时",false),
	
	/**操作频繁*/
	FREQUENCY(3,"操作频繁,请稍后重试",false),
	
	/**无权操作*/
	NOT_PERMISSION(12,"无权操作",false),
	
	/**系统错误*/
	SYS_ERROR(500,"网络错误",false),
	
	/**未知错误*/
	NON_ERROR(9,"未知错误",false),
	
	/**数据操作失败*/
	DB_ERROR(300,"操作失败,请联系管理员",false),
	
	/**未知错误*/
	NOT_FOUND(404,"接口不存在",false);
	
	private Integer code;
	private String msg;
	
	private boolean status;
	
	ResultDic(Integer code,String msg,boolean status){
		this.code = code;
		this.msg = msg;
		this.status = status;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public boolean getStatus() {
		return status;
	}
}
