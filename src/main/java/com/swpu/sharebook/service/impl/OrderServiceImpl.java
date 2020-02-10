package com.swpu.sharebook.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.swpu.sharebook.entity.Book;
import com.swpu.sharebook.entity.BorringStatus;
import com.swpu.sharebook.entity.Order;
import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.mapper.BookMapper;
import com.swpu.sharebook.mapper.OrderMapper;
import com.swpu.sharebook.mapper.UserMapper;
import com.swpu.sharebook.service.OrderService;
import com.swpu.sharebook.shiro.util.UserUtil;
import com.swpu.sharebook.util.Tools;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.swpu.sharebook.mapper.UserOrderStatusMapper;


@Service
public class OrderServiceImpl implements OrderService {
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private BookMapper bookMapper;
	@Resource
	private UserMapper userMapper;

	@Resource
	private UserOrderStatusMapper userOrderstatusMapper;

	@Override
	@Transactional
	public ResponseResult addOrder(Order order, boolean flag) {
		User user=new User();
		if (Tools.isNull(order.getBook().getBId())) {
			return ResponseResult.ERROR(201, "书籍不能为空");
		}
		if (order.getBookAccount() == null || order.getBookAccount() == 0) {
			return ResponseResult.ERROR(204, "请输入你需要选择的书籍数量");
		}
		// 用户输入的书籍数量
		int account = order.getBookAccount();
		// 创建一个空的book对象来保存书籍的数量
		Book book = new Book();
		/**
		 * 下面是通过书籍的id找到书籍目前的数量
		 */
		Map<String, Object> map = new HashMap<>();
		// 返回的而结果集
		map.put("result", "bookAccount");
		// 插入的列名
		map.put("column", "bId");
		// 列对应的值
		map.put("value", order.getBook().getBId());
		// 目前所剩下的书籍的数量
		Integer bookAccount = bookMapper.getBookReturnInt(map);
		// 判断书籍是否足够
		if (Tools.isNull((bookAccount))) {
			return ResponseResult.ERROR(203, "没有你选定的书籍");
		}
		if (bookAccount == 0) {
			return ResponseResult.ERROR(214, "书籍已经被借完啦");
		}
		if (judgeGrades(bookAccount, account, null) != 1 && judgeGrades(bookAccount, account, null) != 2) {
			return ResponseResult.ERROR(207, "书籍库存不足");
		}

		// 判断积分是否足够；
		// 获取用户积分
		user.setId(UserUtil.getUserId());
		//通过用户id获取用户积分
		user.setIntegration(userMapper.getIntegration(user.getId()));
		Integer integration = user.getIntegration();
		// 获取目前的是配送还是自取；
		// 獲取用戶沒有被審核的訂單
		Order orderFlag = new Order();
		orderFlag.setUser(user);
		// 订单状态为正常
		// 不要设置标志为true 否则会查出所有结果集合
		// orderFlag.setOrderStatus(true);
		// 獲取用戶的積分
		List<Order> list = orderMapper.getListAboutOrder(orderFlag);
		int judgeGrades = judgeGrades(integration, account, list);
		BorringStatus borringStatus = new BorringStatus();
		borringStatus.setBId(order.getBook().getBId());
		borringStatus.setUserId(user.getId());
		borringStatus.setBorrwingStatus(flag);
		// 先查看当前是否由该订单
		List<BorringStatus> lists = userOrderstatusMapper.selectBorringStatus(borringStatus);
		if (lists != null && lists.size() != 0) {
			return ResponseResult.ERROR(216, "订单已经生成了，请不要重复提交哦");
		}
		borringStatus.setLoanHour(LocalDateTime.now());
		if (Tools.isNull(order.getDistrbutionId())) {
			// 配送部分为空的话
			// 如果为空的话默认为1:
			if (judgeGrades == 0) {
				if (flag) {
					return ResponseResult.ERROR(205, "积分不足请获取积分无法预定书籍了");
				} else {
					return ResponseResult.ERROR(205, "积分不足无法借阅书籍");
				}

			} else {
				// 创建一个用户书籍状态表

				// 默认为1即没有配送员配送
				order.setDistrbutionId(1);
				order.setOrderStatus(true);
				// 用户积分直接减掉
				integration = integration - account;
				bookAccount = bookAccount - account;
				// 将数据导入到book对象中
				// 更新用户积分;
				// 新建一个用户;
				User userFlag=new User();
				userFlag.setIntegration(integration);
				// 這裏需要報用戶角色也設置進去不然會出現問題
				userFlag.setRole(user.getRole());
				userFlag.setId(user.getId());
				// 更新用户积分
				userMapper.update(userFlag);
				user.setIntegration(integration);
			}
		}
		// 首先判断是否为借阅和与预定都写为false
		borringStatus.setSendStatus(false);
		// 有配送员的情况
		if (!Tools.isNull(order.getDistrbutionId()) && order.getDistrbutionId() != 1) {
			if (judgeGrades == 2) {
				// 目前的状态是没有确认收货的情况
				// order.setOrderStatus(false);
				// 书籍还是要减少account
				bookAccount = bookAccount - account;
				// 将数据导入到book对象中
				// borringStatus借阅状态即被借用
				// borringStatus.setBorrwingStatus(true);
				// 正在配送ture表示正在配送
				if (flag) {
					// 只有是借阅的人员才能由配送服务
					borringStatus.setSendStatus(true);
				}
			} else {
				return ResponseResult.ERROR(202, "你好您的积分不足，请通过其他方式获取积分");
			}
		}
		if (Tools.isNull(order.getOrderBool())) {
			order.setOrderBool(1);
		}
		// 更新用户积分;
		book.setBId(order.getBook().getBId());
		// 更新数据即可
		book.setBookAccount(bookAccount);
		bookMapper.updateBook(book);
		order.setOrderTime(new Date());
		order.setUser(user);
		Integer flagOrder = orderMapper.addOrder(order);
		// 生成书籍用户订单信息
		userOrderstatusMapper.addStatus(borringStatus);
		if (flagOrder == 1) {
		//将订单信息更新
			user=userMapper.getUserById(order.getUser().getId());
			book=bookMapper.getBookById(order.getBook().getBId());
			order.setUser(user);
			order.setBook(book);
			return ResponseResult.SUCCESS("借阅成功", order);
		} else {
			return ResponseResult.ERROR(203, "网络错误");
		}
	}
	/**
	 * 
	 * @param integration
	 * @param account
	 * @param list
	 * @return
	 */
	private static Integer judgeGrades(Integer integration, Integer account, List<Order> list) {
		// 用户积分大于等于1表示用户能够借阅书籍或者预定书籍
		if (list != null) {
			Order order = new Order();
			for (int i = 0; i < list.size(); i++) {
				order = list.get(i);
				if (order.isOrderStatus()) {
					// 正常状态就加上值
					account = account + order.getBookAccount();
				}

			}
		}
		if (integration > account && integration < (2 * account)) {
			return 1;
		}
		if (integration > (2 * account)) {
			return 2;
		}
		return 0;
	}

	// 獲取未被審核的用戶的所有訂單所需的價格
	// private Integer getInteger(List<Order>)
	@Transactional
	@Override
	public ResponseResult auditOrder(Order order) {
		/*if (order == null) {
			return ResponseResult.ERROR(211, "您不能选择一个空订单进行审核哦");
		}
		// 订单不为空
		// 获取订单id
		Integer oId = order.getId();
		if (Tools.isNull(oId) || oId == 0) {
			return ResponseResult.ERROR(212, "订单号不能为空或者不合法");
		}
		// 查询对应的订单
		// 查询对应的订单
		// 查询当前订单：*/
		order = orderMapper.getOrderById(order.getId());
		if (order == null) {
			return ResponseResult.ERROR(213, "你没有你输入的订单哦");
		}
		if (!order.isOrderStatus()) {
			return ResponseResult.ERROR(214, "您配送的订单被用户取消了");
		}
		// 通过订单id找到当前用户的配送详情
		BorringStatus borringStatus = new BorringStatus();
		// 将对应的值放到borringStatus;
		borringStatus.setBId(order.getBook().getBId());
		User user=null;
		borringStatus.setUserId(order.getUser().getId());
		// 将标志设置为空
		// borringStatus.setBorrwingStatus(null);
		// 查询数据库
		List<BorringStatus> listBorringStatus = userOrderstatusMapper.selectBorringStatus(borringStatus);
		if (listBorringStatus == null || listBorringStatus.size() == 0) {
			return ResponseResult.ERROR(218, "没有该用户书籍的订单");
		}
		borringStatus = listBorringStatus.get(0);
		if (!borringStatus.getBorrwingStatus()) {
			return ResponseResult.ERROR(219, "该用户的书籍处于预定状态，不能进行配送审核");
		}
		if (!borringStatus.getBorrwingStatus()) {
			return ResponseResult.ERROR(220, "配送审核完成，请不要重复操作");
		}
		// 将配送状态信息设置为false
		borringStatus.setSendStatus(false);
		// 将借阅时间更新
		borringStatus.setLoanHour(LocalDateTime.now());
		// 更新用户书籍配送订单
		userOrderstatusMapper.updateBorringStatus(borringStatus);
		// 目前要求只能是配送员审核订单；不用考虑
		// 将当前用户数据积分+1*account
		// 被借书人的配送减去2*account
		// 订单状态被修改为审核状态
		// 用户在缓存中的信息得到更新
		Integer account = order.getBookAccount();
		 user = userMapper.getUserById(order.getUser().getId());
		User userFlag = new User();
		userFlag.setId(user.getId());
		Integer teGrade = user.getIntegration() - 2 * account;
		userFlag.setIntegration(teGrade);
		userFlag.setRole(user.getRole());
		// 获取当前用户
		User nowUser = null;
		User updateUser = new User();
		Integer grade = nowUser.getIntegration() + account;
		updateUser.setId(nowUser.getId());
		updateUser.setIntegration(grade);
		updateUser.setRole(nowUser.getRole());
		/*
		 * // 处理订单信息5 目前订单没有需要更改的信息 // 建立一个orderFlag Order orderFlag = new Order();
		 * orderFlag.setId(order.getId()); orderFlag.setOrderTime(new Date()); //
		 * orderFlag.setOrderStatus(true);
		 */ // 进行数据库操作
		userMapper.update(userFlag);

		userMapper.update(updateUser);
		/*// 订单更新
		// orderMapper.updateOrder(orderFlag);
		// 执行成功以后才会处理
		nowUser.setIntegration(grade);
		// 缓存更新
		HttpServletRequest request = Tools.getRequest();
		// 获取用户信息
		// 获取请求头的token
		String token = request.getHeader("token");
		// 测试使用的是参数
		// String token=request.getParameter("token");
		// 更新缓存
//		saveObjectByredis.saveObject(token, nowUser);*/
		return ResponseResult.SUCCESS("配送审核成功");
	}

	@Override
	public ResponseResult selectAllOrder() {
		// 获取用户id
		Order order = new Order();
		order.setDistrbutionId(UserUtil.getUserId());
		List<Order> orderList = orderMapper.getListAboutOrder(order);
		if (orderList == null || orderList.size() == 0) {
			return ResponseResult.ERROR(208, "你好，目前没有需要你配送的订单哦");
		}
		// 操作成功把列表返回就行
		return ResponseResult.SUCCESS(orderList);
	}
	@Override
	public ResponseResult getOrder() {
		// 获取当前用户
		User user = new User();
		user.setId(UserUtil.getUserId());
		// 通过用户userId获取用户订单
		// 获取当前用户的所有订单
		Order order = new Order();
		order.setUser(user);
		List<Order> orderList = orderMapper.getListAboutOrder(order);
		return ResponseResult.SUCCESS(orderList);
	}
	@Transactional
	@Override
	public ResponseResult cancelOrder(Order order) {
		// 將當前用戶的id插入到對應的order中
		User user=new User();
		user.setId(UserUtil.getUserId());
		user.setIntegration(userMapper.getIntegration(user.getId()));
		order.setUser(user);
		// 查詢對應的訂單
		List<Order> orders = orderMapper.getListAboutOrder(order);
		if (orders == null || orders.size() == 0) {
			return ResponseResult.ERROR(243, "你沒有對應的訂單哦");
		}
		order = orders.get(0);
		// 判斷訂單是否被取消
		if (!order.isOrderStatus()) {
			return ResponseResult.ERROR(244, "訂單已經被取消了,請不要重複操作");
		}
		// 查询对应的书籍状态表
		BorringStatus borringStatus = new BorringStatus();
		borringStatus.setBId(order.getBook().getBId());
		borringStatus.setUserId(order.getUser().getId());
		List<BorringStatus> listStatus = userOrderstatusMapper.selectBorringStatus(borringStatus);
		if (listStatus == null || listStatus.size() == 0) {
			// 虽然说有订单必然有用户订单表，但是避免有未知错误，我还是写了未知错误处理
			return ResponseResult.ERROR(245, "数据库查询异常");
		}
		borringStatus = listStatus.get(0);
		// 自取的情况下不能取消订单
		if (order.getDistrbutionId() == 1 && borringStatus.getBorrwingStatus()) {
			return ResponseResult.ERROR(246, "您已经取货了不能取消订单了哦");
		}
		// 配送完成后也不能取消订单
		if (order.getDistrbutionId() > 1) {
			if (!borringStatus.getBorrwingStatus()) {
				return ResponseResult.ERROR(247, "您的订单已经由配送员审核配送完毕，不能取消了");
			}
			if (!borringStatus.getBorrwingStatus()) {
				// 将积分加上即可，使用一个中间用户user
				User tempUser = new User();
				tempUser.setId(user.getId());
				Integer integration = user.getIntegration() + order.getBookAccount();
				tempUser.setIntegration(integration);
				// 更新用户积分
				tempUser.setRole(user.getRole());
				userMapper.update(tempUser);
				user.setIntegration(integration);
			}
		}
		// 如果为预约：的话直接就删除即可
		// 删除对应的数据
		userOrderstatusMapper.delete(borringStatus);
		// 借阅状态需要更新用户数据
		// 更新书籍
		Book book = new Book();
		book.setBId(order.getBook().getBId());
		Map<String, Object>map=Tools.getMap(order.getBook().getBId());
		
		Integer bookAccount = bookMapper.getBookReturnInt(map);
		bookAccount = bookAccount + order.getBookAccount();
		book.setBookAccount(bookAccount);
		bookMapper.updateBook(book);
		// 将订单状态设置为false
		Order tempOrder = new Order();
		tempOrder.setId(order.getId());
		tempOrder.setOrderStatus(false);
		tempOrder.setOrderTime(new Date());
		orderMapper.updateOrder(tempOrder);
		return ResponseResult.SUCCESSM("成功取消订单");
		// 借阅分为配送或者自取：如果是自取的话和预定是完全一样的，不支持取消自取
	}
	@Override
	@Transactional
	public ResponseResult returnBook(Order order) {
		User user=new User();
		user.setId(UserUtil.getUserId());
		user.setIntegration(userMapper.getIntegration(user.getId()));
		//归还书籍
		if(order==null) {
			return ResponseResult.ERROR(250, "归还书籍失败");
		}
		if(order.getId()==null) {
			return ResponseResult.ERROR(256, "订单id不能为空");
		}
		if(order.getId()<=0){
			return ResponseResult.ERROR(252, "书籍id不能为小于0");
		}
		 order=orderMapper.getOrderById(order.getId());
		 if(!order.isOrderStatus()) {
				return ResponseResult.ERROR(251, "书籍 订单已经被取消了");
			}
		 //获取用户书籍状态
		 BorringStatus borringStatus=new BorringStatus();
		 borringStatus.setBId(order.getBook().getBId());
		 borringStatus.setUserId(order.getUser().getId());
		 //result @其实只要用户订单未被取消那么对应的用户状态表必然存在
		 List<BorringStatus> borringStatusList=userOrderstatusMapper.selectBorringStatus(borringStatus);
		 if(borringStatusList==null) {
			 return ResponseResult.ERROR(252, "用户订单状态数据被意外删除了");
		 }
		//不出意外不会出现空指针异常
		 borringStatus=borringStatusList.get(0);
		 if(!borringStatus.getBorrwingStatus()) {
			 return ResponseResult.ERROR(253, "没有收到书籍，不能归还书籍");
		 }
		 if(borringStatus.getReturnTime()!=null) {
			 return ResponseResult.ERROR(255, "书籍已经被确认归还，请不要重复确认");
		 }
		 //今天的日期
		 Date toDate=new Date();
		 //书籍借阅日期
		LocalDateTime yeDate = borringStatus.getLoanHour();


		Integer flag=Tools.getDay(toDate,new Date(yeDate.toInstant(ZoneOffset.of("GMT+8")).toEpochMilli()) );
		 //书籍的数量+account
		 Book book=new Book();
		 book.setBId(order.getBook().getBId());
		 //书籍数量：
		 Map<String, Object> map=Tools.getMap(order.getBook().getBId());
		 Integer bookAccount=bookMapper.getBookReturnInt(map)+order.getBookAccount();
		 book.setBookAccount(bookAccount);
		 //更新书籍数量
		 bookMapper.updateBook(book);
		 //修改归还时间
		 borringStatus.setReturnTime(LocalDateTime.now());
		 userOrderstatusMapper.updateBorringStatus(borringStatus);
		 //判断用户的时间
		 if(flag>30) {
			 if(flag%10==0) {
				 flag=(flag-30)/10;
			 }else {
				 flag=((flag-30)/10)+1;
			 }
			 //	更新用户积分去了;
			 User tempUser=new User();
			 tempUser.setId(user.getId());
			 Integer grade=user.getIntegration()-flag;
			 tempUser.setIntegration(grade);
			 tempUser.setRole(user.getRole());
			 //更新用户积分；
			 userMapper.update(tempUser);
		 }
		return ResponseResult.SUCCESSM("还书成功");
	}
}
