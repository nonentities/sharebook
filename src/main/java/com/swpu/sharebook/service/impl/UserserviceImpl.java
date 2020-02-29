package com.swpu.sharebook.service.impl;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.mapper.UserMapper;
import com.swpu.sharebook.service.UserBaseService;
import com.swpu.sharebook.service.UserService;
import com.swpu.sharebook.shiro.jwt.JWTUtil;
import com.swpu.sharebook.shiro.util.UserUtil;
import com.swpu.sharebook.util.Tools;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserserviceImpl  extends UserBaseService implements UserService {

	@Resource
	private UserMapper userMapper;

	public static final Long EXPIRE_TIME = Duration.ofMinutes(10000).toMillis();
	@Override
	public ResponseResult login(String userName, String password, String preVerifyCode) {

		User md5User = UserUtil.getMd5User(userName, password);
		User dbUser = userMapper.selectOne(new QueryWrapper<User>()
				.eq("userName", userName)
				//.eq("password", md5User.getPassword())
                .eq("password",password)
		);
		if(null != dbUser){
			String token = JWTUtil.generatorToken(String.valueOf(dbUser.getId()), EXPIRE_TIME);
			return ResponseResult.SUCCESS("登陆成功返回了token数据",token);
		}
		return  ResponseResult.ERROR(102, "用户名或者密码错误找不到该用户");
	}

	@Override
	public ResponseResult userLoginService(String userName, String password, String preVerifyCode ) throws Exception {
		//判断用户名密码是否为空
		boolean flag= Tools.isNull(userName,password);
		if(!flag) {
			//验证验证码
//			HttpSession session=Tools.getSession();
//			String verifyCode=(String) session.getAttribute("verifyCode");
//			if(verifyCode==null) {
//				return ResponseResult.ERROR(105,"验证码为空，请输入验证码");
//			}
//			if(!verifyCode.equals(preVerifyCode)) {
//				return ResponseResult.ERROR(106, "验证码输入错误，请重新输入");
//			}
			//查询数据库
			User user = userMapper.getUserByNameAndPssword(Tools.getMap("userName",userName,"password",password));
			//判断是否为空
			if(user==null) {
				return ResponseResult.ERROR(102, "用户名或者密码错误找不到该用户");
			}else {
				//缓存用户
				String token=cacheLoginUser(user);
				return ResponseResult.SUCCESS("登陆成功返回了token数据", token);
			}
		}
		else {
			//返回用户名或者密码为空
			return ResponseResult.ERROR(101,"用户名或者密码为空");
		}
	}

	@Override
	public User findByUserStudentId(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseResult register(User user, String preVerifyCode,String configName) {
		String userName=user.getUserName();
		String password=user.getPassword();
		/*//用户为空
		if(user==null) {
			return ResponseResult.ERROR(107, "请输入正确的信息");
			
		}
		//验证码为空；
		if(preVerifyCode==null) {
			return ResponseResult.ERROR(108, "验证码不能为空");
		}
		//用户名不能为空或者空格
		String userName=user.getUserName();
		if(Tools.isNull(userName)) {
			return ResponseResult.ERROR(109, "用户名不能为空或者空格");
		}
		String password=user.getPassword();
		if(Tools.isNull(password)) {
			return ResponseResult.ERROR(110, "密码不能为空或者空格");
		}
		//微信号不能为空
		if(Tools.isNull(user.getWechatNumber())) {
			return ResponseResult.ERROR(111, "微信号不能是空或者空格");
		}
		//学号不能为空或者空格
		if(Tools.isNull(user.getStudentId())) {
			return ResponseResult.ERROR(112, "学号不能为空或者空格");
		}
		//寝室号不能为空
		if(Tools.isNull(user.getDirmitoryNumber())) {
			return ResponseResult.ERROR(113, "寝室号不能为空");
		}*/

		/**
		 * 上面的我通过数据校验就完成了，不需要再进行处理了
		 */
		//验证码
		HttpSession session=Tools.getSession();
		String verifyCode=(String) session.getAttribute("verifyCode");
		if(verifyCode==null) {
			return ResponseResult.ERROR(105,"验证码为空，请输入验证码");
		}
		if(!verifyCode.equals(preVerifyCode)) {
			return ResponseResult.ERROR(106, "验证码输入错误，请重新输入");
		}
		//确认两次密码是否一致；
		if(!password.equals(configName)) {
			return ResponseResult.ERROR(114, "两次密码不一致，请确认密码");
		}
		//判断数据库中是否有该用户
		User userFlag=userMapper.getUserByNameAndPssword(Tools.getMap("userName",userName,"password",password));
		if(userFlag!=null) {
			return ResponseResult.ERROR(116, "用户你已经注册过请不要重复注册");
		}
		int flag=userMapper.regester(user);
		if(flag==1) {
			//默认第一次用户为普通用户
//			Role  role=new Role();
//			role.setId(1);
//			user.setRole(role);
			return ResponseResult.SUCCESS("注册成功");
		}
		else {
			return ResponseResult.ERROR(115, "注册失败出现未知错误");
		}
	}
	@Override
	public ResponseResult updateUser(User user) {
		//主要是处理密码的问题
		//获取当前用户的id
		user.setId(UserUtil.getUserId());
		user.setIntegration(null);
		//将角色置为空
		user.setRole(null);
		userMapper.update(user);
		return ResponseResult.SUCCESS("修改成功",user);
	}

	@Override
	public ResponseResult updatePassword(String password, String confrimPassword) {
		//获取当前用户的id
		//判断用户密码是否为空或者空格
		if (Tools.isEmpty(password)) {

			return ResponseResult.ERROR(130,"密码不能为空或者空格");
		}
		if (Tools.isEmpty(confrimPassword)) {

			return ResponseResult.ERROR(131,"确认密码不能为空或者空格");

		}
		//对密码格式要求目前省略
		if(!password.equals(confrimPassword)){
			return ResponseResult.ERROR(132,"前后密码不一致");
		}
		Integer id=UserUtil.getUserId();
		Map<String,Object> map=new HashMap<>();
		map.put("id",id);
		map.put("password",password);
		userMapper.updatePassword(map);
		return ResponseResult.SUCCESSM("密码修改成功");
	}

	@Override
	public ResponseResult getCurrentUser() {
		//获取当前用户的id
		Integer id=UserUtil.getUserId();
		User user=userMapper.getUserById(id);
		return ResponseResult.SUCCESS(user);
	}
}
