package com.swpu.sharebook.mapper;

import com.swpu.sharebook.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * 批量更新订单数据
     */
    public void updateOrderBench(@Param("orderLists") List<Order> orderBench);

    /**
     * 获取没有被支付的订单
     */
    public List<Order> getListOrderNotPay(Order order);

    /**
     * 删除订单
     */
    public void deleteOrder(Integer id);
    /**
     * 修改用户还书的状态
     */
    public void updateOrderRetrunStatus(Integer oId);
    /**
     * 获取订单各个状态RRR
     */
    public List<Order>getOrderWaitRRR(Order order);
    /**
     * 获取订单配送员id
     */
    public Integer getDistributeId(Integer oId);
    /**
     * 获取用户的id
     */
    public Integer getUserId(Integer oId);
}