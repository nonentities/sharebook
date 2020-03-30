package com.swpu.sharebook;

import com.swpu.sharebook.entity.Order;
import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.mapper.OrderMapper;
import com.swpu.sharebook.mapper.UserMapper;
import com.swpu.sharebook.shiro.jwt.JWTUtil;
import com.swpu.sharebook.shiro.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SharebookApplicationTests {

   // @Autowired
   // UserMapper userMapper;
@Resource
private OrderMapper orderMapper;
    @Test
   public  void contextLoads() {
        User user = new User();
        user.setId(1);
        // 通过用户userId获取用户订单
        // 获取当前用户的所有订单
        Order order = new Order();
        order.setUser(user);
        order.setIsPay(false);
        List<Order> orderList = orderMapper.getListOrderNotPay(order);
        System.out.println(orderList);
    }
}
