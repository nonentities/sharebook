package com.swpu.sharebook.service;
import java.text.SimpleDateFormat;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class UserBaseService {
	private static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMddHHmmss");
		}
	};
	private static final long CACHE_LOGIN_TIMEOUT = 30;
	private static final String CACHE_LOGIN_PREFIX = "USER_LOGIN_PREFIX_DX004_";
	//@Resource
//	private RedisTemplate<String, Object> redisTemplate;
	/*protected String cacheLoginUser(User user) throws Exception {
		//缓存用户并加密
		List<Object> list = redisTemplate.boundListOps(CACHE_LOGIN_PREFIX.concat(user.getId().toString())).range(0, 120);
		for (Object object : list) {
			redisTemplate.boundListOps(CACHE_LOGIN_PREFIX.concat(user.getId().toString())).remove(1, object);
			redisTemplate.delete((String)object);
		}

		String key = Tools.MD5(user.getId().toString(), "_".concat(sdf.get().format(new Date())));

		redisTemplate.boundListOps(CACHE_LOGIN_PREFIX.concat(user.getId().toString())).rightPush(key);
		redisTemplate.opsForValue().set(key, user,CACHE_LOGIN_TIMEOUT,TimeUnit.MINUTES);
		return key;
	}*/
	/*protected User getCacheLoginUser(String token) {
		//获取缓存用户┖
		if(token==null) {
			return null;
		}
		User user = (User) redisTemplate.opsForValue().get(token);
		if(user != null) {
			cacheLoginUserExpire(token, user);
		}
		return user;
	}*/
	/*protected void clearToken(String token) {
		if(Objects.nonNull(token)) {
			User user = (User) redisTemplate.opsForValue().get(token);
			if(user != null) {
				redisTemplate.delete(CACHE_LOGIN_PREFIX.concat(user.getId().toString()));
			}
			redisTemplate.delete(token);
		}
	}
	protected boolean cacheLoginUserExpire(String token,User user) {	
		List<Object> list = redisTemplate.boundListOps(CACHE_LOGIN_PREFIX.concat(user.getId().toString())).range(0, 120);
		if(list == null || list.size() == 0) {
			return false;
		}
		for (Object object : list) {
			if(!token.equals(object)) {
				redisTemplate.boundListOps(CACHE_LOGIN_PREFIX.concat(user.getId().toString())).remove(1, object);
			}
		}
		return redisTemplate.expire(token, CACHE_LOGIN_TIMEOUT, TimeUnit.MINUTES) 
				&& redisTemplate.expire(CACHE_LOGIN_PREFIX + "user_resource_" + user.getId(),CACHE_LOGIN_TIMEOUT, TimeUnit.MINUTES);
	}
//	protected String cacheLoginverificationCode(String verificationCode) throws Exception {
//		//缓存验证码并加密，和对应的随机数
//		List<Object> list = redisTemplate.boundListOps(CACHE_LOGIN_PREFIX.concat(verificationCode)).range(0, 120);
//		for (Object object : list) {
//			redisTemplate.boundListOps(CACHE_LOGIN_PREFIX.concat(verificationCode)).remove(1, object);
//			redisTemplate.delete((String)object);
//		}
//		String key = Tools.MD5(verificationCode, "_".concat(sdf.get().format(new Date())));
//		redisTemplate.boundListOps(CACHE_LOGIN_PREFIX.concat(verificationCode)).rightPush(key);
//		redisTemplate.opsForValue().set(key, verificationCode,CACHE_LOGIN_TIMEOUT,TimeUnit.MINUTES);
//		return key;
//	}*/
}