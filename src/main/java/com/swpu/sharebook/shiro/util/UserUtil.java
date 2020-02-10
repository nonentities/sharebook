package com.swpu.sharebook.shiro.util;

import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.util.Tools;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class UserUtil {
    /**
     * @param username
     * @param password
     * @return 返回加密后的用户
     */

    public static User getMd5User(String username, String password) {
        User md5User = new User();

        String salt = MD5Util.md5PrivateSalt(username, MD5Util.PUBLIC_KEY);
        String md5Password = MD5Util.md5PrivateSalt(password, salt);

        md5User.setUserName(username);
        md5User.setPassword(md5Password);
        md5User.setSalt(salt);
        return md5User;
    }
    public static User getUser(User user){
        String salt = MD5Util.md5PrivateSalt(user.getUserName(), MD5Util.PUBLIC_KEY);
        String md5Password = MD5Util.md5PrivateSalt(user.getPassword(), salt);
       // user.setUserName();
        user.setPassword(md5Password);
        user.setSalt(salt);
        return user;
    }

    /**
     * 获取用户的id
     * @return
     * @1获取请求头
     * @1获取用户的token
     * @3通过token获取userId
     */
    public static Integer getUserId(){
      String info= SecurityUtils.getSubject().getPrincipal().toString();
      Integer userId=Integer.valueOf(info);
      return userId;
    }
}
