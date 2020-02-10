package com.swpu.sharebook.interceptor;

import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.service.UserService;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import com.swpu.sharebook.util.returnvalue.ResultDic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
//@Component
public class UserInterceptor extends ThreadLocalConst implements HandlerInterceptor {
    @Resource
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取请求头的token
        //	String token = request.getHeader("token");
        String token = request.getParameter("token");
        setToken(token);
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            IgnoreLoginValidate ignoreLoginValidate = handlerMethod.getMethodAnnotation(IgnoreLoginValidate.class);
            if (ignoreLoginValidate == null) {
//				User user = userService.getLoginUser(token);
                User user = null;

                if (user == null) {
                    ResponseResult.ERROR(ResultDic.NOT_LOGIN).throwBizException();
                    return false;
                } else {
                    setUser(user);
                }
            }
        } else {
            System.out.println("当前处理类型不为HandlerMethod,无法进行拦截处理");
        }
        return true;
    }
}
