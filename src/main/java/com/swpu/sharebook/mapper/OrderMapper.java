package com.swpu.sharebook.mapper;

import java.util.List;
import java.util.Map;

import com.swpu.sharebook.entity.Order;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
public Integer addOrder(Order order);
public List<Order> getListAboutOrder(Order order);
public Order getOrderById(Integer id);
public Integer updateOrder(Order order);
public List<Order> getListAoubtUserOrder(Order order);
//判断是否支付
public Integer getIsPay(Integer orderId);
//判断是否被取消
    public Integer getOrderStatus(Integer orderId);
    public Integer updateBool(Map<String, Object> map);
    public Order getOrderByIdOnlyOrder(Integer id);
    /**
     * 更新用户支付方式
     */
    public void updatePay(Map<String, Object> map);
}

