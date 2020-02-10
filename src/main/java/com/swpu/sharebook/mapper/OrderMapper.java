package com.swpu.sharebook.mapper;

import java.util.List;

import com.swpu.sharebook.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
public Integer addOrder(Order order);
public List<Order> getListAboutOrder(Order order);
public Order getOrderById(Integer id);
public Integer updateOrder(Order order);
public List<Order> getListAoubtUserOrder(Order order);
}
