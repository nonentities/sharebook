package com.swpu.sharebook.interceptor;

import com.swpu.sharebook.entity.User;

/**
 * 本地线程请求相关数据缓存
 */
public class ThreadLocalConst {
	
	/**当前后台登录用户数据*/
	private static final ThreadLocal<User> THREAD_LOCAL_USER = new ThreadLocal<>();
	
	/**当前请求token信息*/
	private static final ThreadLocal<String> THREAD_LOCAL_TOKEN = new ThreadLocal<>();
	
	/***
	 * 设置用户数据
	 * @param user 当前登录用户
	 */
	public static void setUser(User user) {
		THREAD_LOCAL_USER.set(user);
	}
	
	/**
	 * 获取当前登录用户
	 * @return 当前登录用户
	 */
	public static User getUser() {
		return THREAD_LOCAL_USER.get();
	}
	
	/***
	 * 设置请求token
	 * @param token token信息
	 */
	public void setToken(String token) {
		THREAD_LOCAL_TOKEN.set(token);
	}
	
	/**
	 * 获取当前请求token信息
	 * @return 当前请求token
	 */
	public static String getToken() {
		return THREAD_LOCAL_TOKEN.get();
	}
}
