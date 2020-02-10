package com.swpu.sharebook.service;

import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.util.returnvalue.ResponseResult;

public interface UserService {

	ResponseResult login(String userName, String password, String preVerifyCode);

	ResponseResult userLoginService(String userName, String password, String preVerifyCode)throws Exception;
	User findByUserStudentId(String string);
	/**
	 * 需要用户输入全部信息；
	 * 包括验证码等等
	 * @param user
	 * @param preVerifyCode
	 * @return
	 */
	ResponseResult register(User user, String preVerifyCode, String configName);
}
