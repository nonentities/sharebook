package com.swpu.sharebook.com.swpu.validator;

import com.swpu.sharebook.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidatorTest {
    //测试并校验user对象
    @Test
    public void testUserValidator(){
      //  User user=new User();
        User user=new User();
        System.out.println(user);
    }
}
