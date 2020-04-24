package com.swpu.sharebook.service.impl;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.entity.UserRole;
import com.swpu.sharebook.mapper.RoleMapper;
import com.swpu.sharebook.mapper.UserMapper;
import com.swpu.sharebook.service.UserBaseService;
import com.swpu.sharebook.service.UserService;
import com.swpu.sharebook.shiro.jwt.JWTUtil;
import com.swpu.sharebook.shiro.util.UserUtil;
import com.swpu.sharebook.util.Tools;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserserviceImpl  extends UserBaseService implements UserService {
	@Resource
	private UserMapper userMapper;
	@Resource
	private RoleMapper roleMapper;
	public static final Long EXPIRE_TIME = Duration.ofMinutes(9000).toMillis();
	@Override
	public ResponseResult login(String userName, String password) {
			HttpSession session=Tools.getSession();
			//String verifyCode=(String) session.getAttribute("verifyCode");
 		/*  if(verifyCode==null) {
				return ResponseResult.ERROR(101,"验证码为空，请输入验证码");
			}
			if(!verifyCode.equals(preVerifyCode)) {
				return ResponseResult.ERROR(102, "验证码输入错误，请重新输入");
			}*/
		User md5User = UserUtil.getMd5User(userName, password);
		User dbUser = userMapper.selectOne(new QueryWrapper<User>()
				.eq("userName", userName)
				//.eq("password", md5User.getPassword())
                .eq("password",password)
		);
		if(null != dbUser){
			String token = JWTUtil.generatorToken(String.valueOf(dbUser.getId()), EXPIRE_TIME);
			return ResponseResult.SUCCESS("登录成功",token);
		}
		return  ResponseResult.ERROR(103, "用户名或密码错误");
	}

	@Override
	public ResponseResult userLoginService(String userName, String password, String preVerifyCode ) throws Exception {
		return null;
	}

	@Override
	public User findByUserStudentId(String string) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	@Transactional
	public ResponseResult register(User user, String preVerifyCode,String configName) {
		String userName=user.getUserName();
		String password=user.getPassword();
		if(preVerifyCode==null) {
			return ResponseResult.ERROR(121, "验证码不能为空");
		}

		HttpSession session=Tools.getSession();
		String verifyCode=(String) session.getAttribute("verifyCode");


		if(verifyCode==null) {
			return ResponseResult.ERROR(122,"验证码为空，请输入验证码");
		}
		if(!verifyCode.equals(preVerifyCode)) {
			return ResponseResult.ERROR(123, "验证码输入错误，请重新输入");
		}

		//确认两次密码是否一致；
		if(!password.equals(configName)) {
			return ResponseResult.ERROR(124, "两次密码不一致，请确认密码");
		}
		//判断数据库中是否有该用户
		User userFlag=userMapper.getUserByNameAndPssword(Tools.getMap("userName",userName,"password",password));
		if(Tools.notNull(userFlag)) {
			return ResponseResult.ERROR(125, "用户你已经注册过请不要重复注册");
		}
		userMapper.regester(user);
		UserRole userRole=new UserRole();
		userRole.setUId(user.getId());
		userRole.setTId(1);
		userRole.setDate(new Date());
		roleMapper.addUserRole(userRole);
			return ResponseResult.SUCCESS("注册成功");
	}
	@Override
	public ResponseResult updateUser(User user) {
		//获取当前用户的id
		user.setId(UserUtil.getUserId());
		user.setIntegration(null);
		//判断是否输入了手机号
		if(Tools.notEmpty(user.getWechatNumber())){
			if(!Tools.checkPhone(user.getWechatNumber())){
				return ResponseResult.ERROR(141,"您输入的手机号格式有误");
			}
		}
		userMapper.update(user);
		return ResponseResult.SUCCESS("修改成功",user);
	}
	@Override
	public ResponseResult updatePassword(String password, String confrimPassword) {
		//获取当前用户的id
		//判断用户密码是否为空或者空格
		Boolean flag=Tools.checkPassword(password);
		if(!flag){
			return ResponseResult.ERROR(134,"密码不能为空或者您输入的密码不合法");
		}
		if (Tools.isEmpty(confrimPassword)) {

			return ResponseResult.ERROR(132,"确认密码不能为空或者空格");

		}
		//对密码格式要求目前省略
		if(!password.equals(confrimPassword)){
			return ResponseResult.ERROR(133,"前后密码不一致");
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
		//String message=null;
		if(Tools.isNull(user)){
			return ResponseResult.SUCCESS(false,null);
		}
		return ResponseResult.SUCCESS(true,user);
	}
}