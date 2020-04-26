package com.swpu.sharebook.controller;

import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.service.UserService;
import com.swpu.sharebook.util.Tools;
import com.swpu.sharebook.util.VerifyCodeUtils;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;

@RestController
//配置userControl下面的所有请求
//为拦截做好了准备，以后每次只需要拦截user下面的所有请求即可
@RequestMapping("/usersControl")
public class UserControl {

    @Resource
    private UserService userService;

    @RequestMapping("/unlogin")
    public ResponseResult unLogin() {
        return ResponseResult.ERROR(500, "请登录！");
    }

    @RequestMapping("/test")
    public ResponseResult test() {

        return ResponseResult.SUCCESS();

    }


    @RequiresRoles({"普通用户"})
    @RequestMapping("/testA")
    public ResponseResult testA() {

        return ResponseResult.SUCCESS();

    }

    /**
     * // @RequestParam("preVerifyCode"
     * @param userName
     * @param password
     * @return
     */
    @PostMapping("/login")
    public ResponseResult login(@RequestParam("username") String userName, @RequestParam("password") String password,@RequestParam("preVerifyCode") String preVerifyCode)throws Exception{

        return userService.login(userName, password,preVerifyCode);
    }

    /**
     * 获取单个字符串的直接使用字符串返回？
     *
     * @return 1session实现了验证码
     * @throws IOException
     */
    //需要过滤掉，直接放行该方法，因为该方法在没用登陆的时候就需要发送请求获取验证码
    @GetMapping("/getVerifyCodeBySession")
    public OutputStream getVerifyCode() throws IOException {
        //获取验证码:
        String verifyCodeName = "verifyCode";
//		  String value=Tools.verifyCode();
//		  Tools.getSession().setAttribute(verifyCodeName, value);
//		    return value;
        //产生验证码；
        String verifyCodeValue = VerifyCodeUtils.generateVerifyCode(4);
        //生成文件
        //System.out.println(value);
        Tools.getSession().setAttribute(verifyCodeName, verifyCodeValue);
        //OutputStream so=new FileOutputStream(value);
        //VerifyCodeUtils.outputImage(60, 60, , code);
        //OutputStream os=new FileOutputStream(new File("G:\\mmqTestFile\\mmq.jpg"));
        //尝试看看能不能直接返回一个流给你
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        OutputStream os = response.getOutputStream();
        //生成图片流的大小
        VerifyCodeUtils.outputImage(2500, 1080, os, verifyCodeValue);
        System.out.println(verifyCodeValue);
        return os;
    }

    //测试时期用get请求，后期用post请求

    /**
     *
     * @param User
     * @param bindingResult 是作为数据校验的结果集，通过bindingResult.hasErrors()来判断是否有错误
     * @param preVerifyCode
     * @param configName
     * @return
     */
    @PostMapping("/register")
    public ResponseResult register(@Valid User user, BindingResult bindingResult, String preVerifyCode, String configName) {
       if(bindingResult.hasErrors()){

            return Tools.saveEorrorMessage(bindingResult);
        }
        return userService.register(user, preVerifyCode, configName);

    }
    @GetMapping("/getCurrentUser")
    public ResponseResult getCurrentUser(){

        return userService.getCurrentUser();
    }
    @PostMapping("/updatePassword")
    public ResponseResult updatePassword(@RequestParam("Password") String password,@RequestParam("confrimPassword") String confrimPassword){
        return userService.updatePassword(password,confrimPassword);
    }
    @PostMapping("updateUser")
    public ResponseResult updateUser(User user,BindingResult bindingResult){
      /*  if(bindingResult.hasErrors()){

            return Tools.saveEorrorMessage(bindingResult);
        }*/
        return userService.updateUser(user);
    }
}
