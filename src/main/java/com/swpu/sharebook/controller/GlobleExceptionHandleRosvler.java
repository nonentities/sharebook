package com.swpu.sharebook.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
@ControllerAdvice
public class GlobleExceptionHandleRosvler implements HandlerExceptionResolver{
@Override
public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) {
	ModelAndView mv=new ModelAndView();
	if(ex instanceof NullPointerException ) {
		mv.addObject("mmqNull",ex);
		mv.setViewName("error1");
	}
	if(ex instanceof ArithmeticException) {
		mv.addObject("mmqMath",ex);
		mv.setViewName("errror2");
		
	}
	return mv;
}
}
