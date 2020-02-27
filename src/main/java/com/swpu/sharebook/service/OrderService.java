package com.swpu.sharebook.service;


import com.swpu.sharebook.entity.Order;
import com.swpu.sharebook.util.returnvalue.ResponseResult;

import java.util.List;

public interface OrderService {
/**
 * 添加订单；
 * 包括借还是预定
 * flag表示的是借还是订书
 * /**
 * @lendBook
 * @param order
 * @return
 *前端传入订单，业务需求：
 *@1 分两种情况：
 *1用户自取：
 *即借书要求：#1 用户积分》1；，借一本书积分减少1，以后会有关于借书增加信誉积分的问题（暂不考虑）
 *2用户要求配送 ：#1 》2；借一本书积分减少2：配送员积分+1（这里需要用户确认订单后才会处理）；
 *要求配送需要当前用户的
 *默认自取信息为0
 *自取的话直接生成订单：
 *配送需求用户确认到货；（一天内无条件到货）
 */
	public ResponseResult addOrder(Order order);
	/**
	 * 配送审核订单
	 * 首先审核订单只能是由配送员及其以上人员确认
	 * @param order 
	 * @auditOrder
	 * 从当前uid下找到对应的用户的id获取配送员的订单
	 * 设计思路
	 * 配送员的积分+2即可
	 * @3订单时间修改为当前时间，订单状态修改为已经配送
	 */
	public ResponseResult auditOrder(Order order);
	/**
	 * 获取当前用户需要配送的所有的用户订单信息
	 * @return
	 * @1没有则返回，配送员你需要努力了，你目前没有需要配送的订单哦
	 * @2返回用户的订单
	 */
	public ResponseResult selectAllOrder(); 
	/**
	 * 获取当前的用户的所有订单信息
	 * @return
	 */
	public ResponseResult getOrder();
	/**
	 *取消订单：
	 *判断是否有该订单：
	 *判断是否属于配送：{预约的可以取消订单，正在配送就可以取消订单}
	 *书籍数量+订单数量：
	 *用户的积分  积分不做处理：如果是预约的话就加上积分
	 * @param order
	 * @return
	 */
	public ResponseResult cancelOrder(Order order);
	/**
	 * 归还书籍：
	 * 1：按期归还：
	 * 2：超期归还：
	 * 3：1修改订单书籍状态表：
	 * 4：修改用户书籍表
	 * @param order
	 * @return
	 */
	public ResponseResult returnBook(Order order);
	/**
	 * 用户购买功能
	 * 1判断用的购买方式
	 * 1：预定：积分不变
	 * 2：借阅：
	 * 			1自取：用户积分减少account*bookPrice
	 *  		2配送:：用户积分减少account*bookPrice;
	 *  	重点是在于用户积分的问题上面处理
	 */
	public ResponseResult payOrder(Integer id, boolean flag);
/**
 * 获取未被支付的订单
 */
public ResponseResult getDontPay();
/***
 * 批量支付
 */
public ResponseResult payBench(Integer[] orderLists,Integer distrubutionId);
}
