package com.swpu.sharebook.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
/**
 * 浣跨敤鍏ㄥ眬鐨勫紓甯稿鐞嗘柟娉�
 * 
 * 1:浼樺娍 control鐨勬墍鏈夊紓甯搁兘浼氬湪杩欓噷澶勭悊,鏂逛究浠ｇ爜閲嶇敤
 * @author mmq
 * 2鍘熷ExceptionControlHandl鍙互浣跨敤鐨勬槸
 *
 */
@ControllerAdvice
public class GlobleExceptionHandle {
	//鎸囧畾value灞炴�ф寚鍚戝搴旂殑寮傚父绫�
	//鍏朵腑value鏄竴涓暟缁�
@ExceptionHandler(value= {NullPointerException.class})
	public ModelAndView getNullPointException(Exception e) {
		ModelAndView mv=new ModelAndView();
		//灏嗗紓甯稿姞鍏odelAndview
		mv.addObject("mmq",e);
		//鎸囧畾璺宠浆鍒板摢涓〉闈�
		mv.setViewName("mmq");
		return mv;
	}
}
