package com.swpu.sharebook;

import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.mapper.UserMapper;
import com.swpu.sharebook.shiro.jwt.JWTUtil;
import com.swpu.sharebook.shiro.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SharebookApplicationTests {

   // @Autowired
   // UserMapper userMapper;

   // @Test
    void contextLoads() {
    //    User user = userMapper.selectById(25);
    //    System.out.println(user);

//        User admin = UserUtil.getMd5User("admin", "123");
//        user.setPassword(admin.getPassword());
//        user.setSalt(admin.getSalt());
//        System.out.println(user);
//        userMapper.updateById(user);
    }

}
