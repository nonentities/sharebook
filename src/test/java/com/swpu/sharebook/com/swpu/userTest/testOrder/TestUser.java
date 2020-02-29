package com.swpu.sharebook.com.swpu.userTest.testOrder;

import com.swpu.sharebook.entity.Book;
import com.swpu.sharebook.entity.Order;
import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.mapper.OrderMapper;
import com.swpu.sharebook.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class TestUser {
    @Resource
    private UserMapper userMapper;
    @Test
    public void testSendUser(){
List<User> users=userMapper.sendUsers();
        System.out.println(users);
    }
}
