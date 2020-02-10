package com.swpu.sharebook.com.swpu.com.swpu.sharebook.testOrder;

import com.swpu.sharebook.entity.Book;
import com.swpu.sharebook.entity.Order;
import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.mapper.OrderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class TestOrder {
    @Resource
    private OrderMapper orderMapper;
    @Test
    public void testOrder(){
        Order order=new Order();
        Book book=new Book();
        book.setBId(32);
        order.setBook(book);
        order.setUser(new User());
        List<Order> orders=orderMapper.getListAboutOrder(order);
        System.out.println(orders);
    }
}
