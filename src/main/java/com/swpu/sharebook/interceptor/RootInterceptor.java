package com.swpu.sharebook.interceptor;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.entity.createentity.RootAndAuthority;
import com.swpu.sharebook.mapper.RootMapper;
import com.swpu.sharebook.util.Tools;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
/**
 * 权限拦截器
 * @author mmq
 */
@Slf4j
@Component
public class RootInterceptor extends ThreadLocalConst implements HandlerInterceptor {

	@Resource
	private RootMapper rootMapper;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/**
		 * 权限拦截器
		 * @1 通过用户权限获取用户的权限
		 * @2 获取当前请求的uri
		 * @3 与数据库匹配并获得数据库的值
		 * 引入数据库，并把权限放入缓存中，防止重复操作数据库
		 * ？same :同行，different 阻止拦截
		 */
		//直接从当前线程找User
		User user=ThreadLocalConst.getUser();
		//先从缓存中通过用户学号查找用户权限：
		List<RootAndAuthority> list= null;
		if(list==null) {
			System.out.println("没有存入缓存或者已经过期");
			//从数据库中获取
			list=rootMapper.getAuthorityByRoleId(user.getRole().getRId());
			//存入缓存
//			saveObjectByredis.saveObject(user.getStudentId(),list);
		}
		StringBuffer url= Tools.getRequest().getRequestURL();
		boolean flag=Tools.compareString(list,url);
		if(flag) {
			System.out.println("用户有该权限，放行");
			return true;
		}else {
			System.out.println("没有权限不能操作");
			ResponseResult.ERROR(300, "没有权限处理");
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}
