package com.swpu.sharebook.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.swpu.sharebook.entity.Book;
import com.swpu.sharebook.entity.Order;
import com.swpu.sharebook.service.OrderService;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderControl")
public class OrderControl {
	@Resource
	private OrderService orderService;

	// 借阅书籍
	@GetMapping("addOrder")
	public ResponseResult addOrder(@RequestParam("bId") Integer bId,@RequestParam("bookAccount") Integer bookAccount, boolean flag) {
		Order order=new Order();
		Book book=new Book();
		book.setBId(bId);
		order.setBook(book);
		order.setBookAccount(bookAccount);
		return orderService.addOrder(order, flag);
	}
	// 订单确认：
	@GetMapping("auditOrder")
	public ResponseResult auditOrder(Order order) {
		return orderService.auditOrder(order);
	}
	/**
	 * 查看当前用户所有需要配送并确认的的订单
	 * 
	 * @selectAllOrder
	 */
	@GetMapping("selectAllOrder")
	public ResponseResult selectAllOrder() {
		return orderService.selectAllOrder();
	}

	@GetMapping("getOrder")
	public ResponseResult getOrder() {
		return orderService.getOrder();
	}

	@GetMapping("cancelOrder")
	public ResponseResult cancelOrder(Order order) {
		return orderService.cancelOrder(order);
	}
	@GetMapping("returnBook")
	public ResponseResult returnBook(Order order) {
		return orderService.returnBook(order);
	}
}
