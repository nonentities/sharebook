package com.swpu.sharebook.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration  //spring redis session的注解这里不用理会
public class Config implements WebMvcConfigurer {
    /*
     * @Autowired private LoginInterceptor loginInterceptor;
     */
    @Autowired
    private UserInterceptor userInterceptor;
    @Autowired
    private RootInterceptor rootInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//    	//登陆拦截器
//        registry.addInterceptor(userInterceptor).addPathPatterns("/**")
//        .excludePathPatterns("/usersControl/login","/usersControl/getVerifyCodeBySession","/bookBaseControl/selectBookByName"
//        		,"/bookBaseControl/selectBook","/usersControl/register","/bookBaseControl/getBookByKey");
//        //配置权限拦截器
//        registry.addInterceptor(rootInterceptor).addPathPatterns("/**")
//        .excludePathPatterns("/usersControl/login","/usersControl/getVerifyCodeBySession","/bookBaseControl/selectBookByName"
//        		,"/bookBaseControl/selectBook","/bookBaseControl/getBookDontAudit","/usersControl/register","/bookBaseControl/getBookByKey","/bookBaseControl/getSendOrder","/orderControl/getOrder","/orderControl/cancelOrder");
    }
}
