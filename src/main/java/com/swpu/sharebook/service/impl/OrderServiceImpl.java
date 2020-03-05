package com.swpu.sharebook.service.impl;

import com.swpu.sharebook.entity.Book;
import com.swpu.sharebook.entity.BorringStatus;
import com.swpu.sharebook.entity.Order;
import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.entity.createentity.BIdAndBookAccount;
import com.swpu.sharebook.mapper.BookMapper;
import com.swpu.sharebook.mapper.OrderMapper;
import com.swpu.sharebook.mapper.UserMapper;
import com.swpu.sharebook.mapper.UserOrderStatusMapper;
import com.swpu.sharebook.service.OrderService;
import com.swpu.sharebook.shiro.util.UserUtil;
import com.swpu.sharebook.util.Tools;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;


@Service
public class OrderServiceImpl implements OrderService {
	@Resource
	private OrderMapper orderMapper;
	@Resource
	private BookMapper bookMapper;
	@Resource
	private UserMapper userMapper;

	@Resource
	private UserOrderStatusMapper userOrderStatusMapper;

	@Override
	@Transactional
	public ResponseResult addOrder(Order order) {
		if (Tools.isNull(order.getBook().getBId())) {
			return ResponseResult.ERROR(201, "书籍不能为空");
		}
		if (order.getBookAccount() == null || order.getBookAccount() == 0) {
			return ResponseResult.ERROR(204, "请输入你需要选择的书籍数量");
		}
		if (Tools.isNull(order.getOrderBool())) {
			order.setOrderBool(1);
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
		//创建一个user对象
		User user=new User();
		user.setId(UserUtil.getUserId());
		order.setUser(user);
		orderMapper.addOrder(order);
		return ResponseResult.SUCCESSM("成功加入购物车");
	}
/*	*//**
	 * 
	 * @param
	 * @param
	 * @param
	 * @return
	 *//*
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
	}*/

	// 獲取未被審核的用戶的所有訂單所需的價格

	@Transactional
	@Override
	public ResponseResult auditOrder(Order order) {
		// 查询对应的订单
		// 查询对应的订单
		// 查询当前订单：*/
		order = orderMapper.getOrderById(order.getId());
		if (order == null) {
			return ResponseResult.ERROR(213, "订单id不能为空的");
		}
		if(order.getDistrbutionId()!= UserUtil.getUserId()){
			return ResponseResult.ERROR(220,"不是您的订单不能进行审核");
		}
		if(!order.isPay()){
			return ResponseResult.ERROR(221,"目前该订单没有支付的哦");
		}
		if (!order.isOrderStatus()) {
			return ResponseResult.ERROR(214, "您配送的订单被用户取消了");
		}
		// 通过订单id找到当前用户的配送详情
		List<BorringStatus> borringStatusList=userOrderStatusMapper.getSendOrderStatus(order.getDistrbutionId());
		if (borringStatusList == null || borringStatusList.size() == 0) {
			return ResponseResult.ERROR(218, "没有该用户书籍的订单");
		}
	/*	if (!borringStatus.getBorrwingStatus()) {
			return ResponseResult.ERROR(219, "该用户的书籍处于预定状态，不能进行配送审核");
		}*/
/*		if (!borringStatus.getSendStatus()) {
			return ResponseResult.ERROR(220, "配送审核完成，请不要重复操作");
		}
		// 将配送状态信息设置为false*/
	//	borringStatus.setSendStatus(false);
		// 将借阅时间更新
		for(int i=0;i<borringStatusList.size();i++){
			borringStatusList.get(i).setLoanHour(LocalDateTime.now());
		}
		// 更新用户书籍配送订单
		userOrderStatusMapper.updateBorringStatusBench(borringStatusList);
		Integer account = order.getBookAccount();
		//获取当前用户积分
		//配送成功后配送员积分+2
		Integer grade=userMapper.getIntegration(UserUtil.getUserId())+2;
		Map<String ,Integer> map=new HashMap<>();
		map.put("id", UserUtil.getUserId());
		map.put("integration",grade);
		userMapper.updateIntegration(map);
		//减少借书人的积分
		grade=userMapper.getIntegration(order.getUser().getId())-2;
		map.put("id",order.getUser().getId());
		map.put("integration",grade);
		userMapper.updateIntegration(map);
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
		if (Tools.isNull(order.getId())) {

			return ResponseResult.ERROR(249,"请输入需要删除的订单id");
		}
		// 將當前用戶的id插入到對應的order中
		//List<Order> orders = orderMapper.getListAboutOrder(order);
		order=orderMapper.getOrderById(order.getId());
		if (Tools.isNull(order)) {
			return ResponseResult.ERROR(243, "你沒有對應的訂單哦");
		}
		// 判斷訂單是否被取消
		if (!order.isOrderStatus()) {
			return ResponseResult.ERROR(244, "訂單已經被取消了,請不要重複操作");
		}
		if(!order.isPay()){
			return  ResponseResult.ERROR(248,"订单还未支付，不能取消");
		}
		if(order.isSendStatus()){

			//只有待配送的订单可以取消

			//1删除对应的用户订单信息字段
			BorringStatus borringStatus=new BorringStatus();
			borringStatus.setBId(order.getId());
			borringStatus.setUserId(order.getUser().getId());
			userOrderStatusMapper.delete(borringStatus);
			Map<String,Object> map=new HashMap<>();
			//2将用户订单的支付状态改为false
/*
			map.put("id",order.getId());
			map.put("isPay",false);
			orderMapper.updatePay(map);*/
			//2将用户对应的积分返回
			Map<String, Integer> mapGrade=new HashMap<>();
			mapGrade.put("id",order.getUser().getId());
			Integer grade=userMapper.getIntegration(order.getUser().getId())+(order.getBook().getBookPrice()*order.getBookAccount());
			mapGrade.put("integration",grade);
			userMapper.updateIntegration(mapGrade);
			//4将订单状态改为false
			 order.setOrderStatus(false);
			order.setPay(false);
			 orderMapper.updateOrder(order);
			 //5更新书籍库存
			Integer bookAccount=bookMapper.getBookAccount(order.getBook().getBId())+order.getBookAccount();
			map.put("bookAccount",bookAccount);
			map.put("bId",order.getBook().getBId());
			bookMapper.updateBookAccount(map);
			return ResponseResult.SUCCESSM("成功取消订单");
		}
		return ResponseResult.ERROR(245,"书籍已经在您的手上了，不能取消订单了");
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
				return ResponseResult.ERROR(251, "书籍订单已经被取消了");
			}
		 //获取用户书籍状态
		 BorringStatus borringStatus=new BorringStatus();
		 borringStatus.setBId(order.getBook().getBId());
		 borringStatus.setUserId(order.getUser().getId());
		 //result @其实只要用户订单未被取消那么对应的用户状态表必然存在
		 List<BorringStatus> borringStatusList=userOrderStatusMapper.selectBorringStatus(borringStatus);
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
		Integer flag= Tools.getDay(toDate,new Date(yeDate.toInstant(ZoneOffset.of("GMT+8")).toEpochMilli()) );
		 //书籍的数量+account
		 Book book=new Book();
		 book.setBId(order.getBook().getBId());
		 //书籍数量：
		 Map<String, Object> map= Tools.getMap(order.getBook().getBId());
		 Integer bookAccount=bookMapper.getBookReturnInt(map)+order.getBookAccount();
		 book.setBookAccount(bookAccount);
		 //更新书籍数量
		 bookMapper.updateBook(book);
		 //修改归还时间
		 borringStatus.setReturnTime(LocalDateTime.now());
		 userOrderStatusMapper.updateBorringStatus(borringStatus);
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
	@Transactional
	public ResponseResult payOrder(Integer id, Integer distrubutionId){
		if(Tools.isNull(id)){
			return ResponseResult.ERROR(216,"订单id不能为空");
		}
		Order order=orderMapper.getOrderByIdOnlyOrder(id);
		if(order==null){
			return ResponseResult.ERROR(217,"不存在您所输入的订单");
		}
		if(order.isPay()){
			return ResponseResult.ERROR(218,"目前订单已经被支付了，不需要再支付了哦");
		}
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
		bookAccount=bookAccount-account;
		if (bookAccount == 0) {
			return ResponseResult.ERROR(214, "书籍已经被借完啦不足以支付您目前的订单");
		}
		// 判断积分是否足够；
		// 获取用户积分
		Integer userId= UserUtil.getUserId();
		//Integer gration=this.getIntergation(userId);
		Integer gration=userMapper.getIntegration(userId);
		//判断是否为负数
		if(gration<1){
			return ResponseResult.ERROR(219,"您的余额已不足");
		}
		//书籍的价格
		Integer price=order.getBook().getBookPrice();
		gration=gration-(price *account);
		if(gration<0){
			return ResponseResult.ERROR(220,"您的余额不足以购买当前书籍");
		}
		BorringStatus borringStatus = new BorringStatus();
		borringStatus.setBId(order.getBook().getBId());
		borringStatus.setUserId(userId);
		borringStatus.setBorrwingStatus(true);
		// 先查看当前是否有该订单
		List<BorringStatus> lists = userOrderStatusMapper.selectBorringStatus(borringStatus);
		if (lists != null && lists.size() != 0) {
			return ResponseResult.ERROR(216, "订单已经生成了，请不要重复提交哦");
		}
		borringStatus.setLoanHour(LocalDateTime.now());
//单个支付订单的话需要收取1积分的配送费
        if (Tools.isNull()) {
            borringStatus.setSendStatus(false);
        }else{
            borringStatus.setSendStatus(true);
            order.setDistrbutionId(distrubutionId);
            gration=gration-1;
        }
		order.setOrderStatus(true);
		book.setBId(order.getBook().getBId());
		// 更新数据即可
		book.setBookAccount(bookAccount);
		bookMapper.updateBook(book);
		order.setOrderTime(new Date());
		//order.setUser(user);
		order.setPay(true);

		//更新订单信息
		Integer flagOrder = orderMapper.updateOrder(order);
		// 生成书籍用户订单信息
		userOrderStatusMapper.addStatus(borringStatus);
		//更新用户积分信息
		Map<String ,Integer> mapInteger=new HashMap<>();
		mapInteger.put("integration",gration);
		mapInteger.put("id", UserUtil.getUserId());
		userMapper.updateIntegration(mapInteger);
		return ResponseResult.SUCCESSM("支付成功");
	}

	@Override
	public ResponseResult getDontPay() {
		User user = new User();
		user.setId(UserUtil.getUserId());
		// 通过用户userId获取用户订单
		// 获取当前用户的所有订单
		Order order = new Order();
		order.setUser(user);
		order.setPay(false);
		List<Order> orderList = orderMapper.getListOrderNotPay(order);
		return ResponseResult.SUCCESS(orderList);
	}
@Override
	public ResponseResult payBench(Integer [] orderLists,Integer distrubutionId){
		//获取用户的数据
		if(Tools.isNull(orderLists)||orderLists.length==0){
			return ResponseResult.ERROR(260,"您没有选中订单");
		}
		//创建一个Map来临时接收书籍的库存
		Map<Integer, BIdAndBookAccount> mapTempBook=new HashMap<>();
		//创建临时的list来接收order
		List<Order> orderBench=new ArrayList<>();
		//创建临时的list来接收用户订单状态
		List<BorringStatus> borringStatusList=new ArrayList<>();
		//获取当前用户积分
		Integer gration=userMapper.getIntegration(UserUtil.getUserId());
		//支付状态
	//判断是否为配送
	if(Tools.isNull(distrubutionId)||distrubutionId<=0){
	    distrubutionId=-1;
		for (int i = 0; i < orderLists.length; i++) {
			//用order对了来接收单个的订单id
			Integer orderId = orderLists[i];
			if (Tools.isNull(orderId)) {
				return ResponseResult.ERROR(261, "目前第" + (i + 1) + "个i订单的id为空");
			}
			Order order = orderMapper.getOrderByIdOnlyOrder(orderId);
			if (order == null) {
				return ResponseResult.ERROR(262, "您目前第" + (i + 1) + "个订单不存在");
			}
			if (order.isPay()) {
				return ResponseResult.ERROR(263, "目前" + (i + 1) + "订单已经被支付了，不需要再支付了哦");
			}
			int account = order.getBookAccount();
			// 书籍数量问题，与批量操作密切相关(重点)
			/**
			 * @1从map中找是否有对应bId的书籍数量，
			 * 判断：如果没有：则从数据库中获取
			 * 		如果有：则从map中找并直接取出来
			 * 		判断id是否为0：
			 * 		如果为0 直接返回即可
			 * 		如果不为0：书籍的书籍-订单中书籍的数量
			 * 		判断最终结果是否为0
			 * 		如果不为0 则将数据重新放入到map中去
			 * 	最后批量更新map即可
			 */
			Integer bId = order.getBook().getBId();
			BIdAndBookAccount bIdAndBookAccount = mapTempBook.get(bId);
			if (Tools.isNull(bIdAndBookAccount)) {
				bIdAndBookAccount = new BIdAndBookAccount();
				Integer bookAccount = bookMapper.getBookAccount(bId);
				bIdAndBookAccount.setBId(bId);
				bIdAndBookAccount.setBookAccount(bookAccount);
			}
			bIdAndBookAccount.setBookAccount(bIdAndBookAccount.getBookAccount() - account);
			if (bIdAndBookAccount.getBookAccount() < 0) {
				String bName = bookMapper.getBNameById(bId);
				return ResponseResult.ERROR(265, "请核查订单，" + (i + 1) + "个订单支付时出现了" + bName + "书籍库存不足");
			}
			//更新map
			mapTempBook.put(bId, bIdAndBookAccount);
			// 判断积分是否足够；
			// 获取用户积分
			Integer userId = UserUtil.getUserId();
			//判断是否为负数
			if (gration < 1) {
				return ResponseResult.ERROR(219, "您的余额已不足");
			}
			//书籍的价格
			Integer price = order.getBook().getBookPrice();
			gration = gration - (price * account);
			if (gration < 0) {
				return ResponseResult.ERROR(220, "您的余额不足");
			}
			BorringStatus borringStatus = new BorringStatus();
			borringStatus.setBId(order.getBook().getBId());
			borringStatus.setUserId(userId);
			borringStatus.setBorrwingStatus(true);
			// 先查看当前是否有该订单
			List<BorringStatus> lists = userOrderStatusMapper.selectBorringStatus(borringStatus);
			if (lists != null && lists.size() != 0) {
				return ResponseResult.ERROR(216, "第" + (i + 1) + "订单已经生成了，请不要重复提交哦");
			}
			borringStatus.setLoanHour(LocalDateTime.now());
				borringStatus.setSendStatus(false);
				order.setDistrbutionId(1);
			order.setOrderStatus(true);
			order.setOrderTime(new Date());
			order.setPay(true);
			order.setDistrbutionId(distrubutionId);
			orderBench.add(order);
			borringStatusList.add(borringStatus);
		}
	}
	else{
		gration=gration-2;
		for (int i = 0; i < orderLists.length; i++) {
			//用order对了来接收单个的订单id
			Integer orderId = orderLists[i];
			if (Tools.isNull(orderId)) {
				return ResponseResult.ERROR(261, "目前第" + (i + 1) + "个i订单的id为空");
			}
			Order order = orderMapper.getOrderByIdOnlyOrder(orderId);
			if (order == null) {
				return ResponseResult.ERROR(262, "您目前第" + (i + 1) + "个订单不存在");
			}
			if (order.isPay()) {
				return ResponseResult.ERROR(263, "目前" + (i + 1) + "订单已经被支付了，不需要再支付了哦");
			}
			int account = order.getBookAccount();
			// 书籍数量问题，与批量操作密切相关(重点)
			/**
			 * @1从map中找是否有对应bId的书籍数量，
			 * 判断：如果没有：则从数据库中获取
			 * 		如果有：则从map中找并直接取出来
			 * 		判断id是否为0：
			 * 		如果为0 直接返回即可
			 * 		如果不为0：书籍的书籍-订单中书籍的数量
			 * 		判断最终结果是否为0
			 * 		如果不为0 则将数据重新放入到map中去
			 * 	最后批量更新map即可
			 */
			Integer bId = order.getBook().getBId();
			BIdAndBookAccount bIdAndBookAccount = mapTempBook.get(bId);
			if (Tools.isNull(bIdAndBookAccount)) {
				bIdAndBookAccount = new BIdAndBookAccount();
				Integer bookAccount = bookMapper.getBookAccount(bId);
				bIdAndBookAccount.setBId(bId);
				bIdAndBookAccount.setBookAccount(bookAccount);
			}
			bIdAndBookAccount.setBookAccount(bIdAndBookAccount.getBookAccount() - account);
			if (bIdAndBookAccount.getBookAccount() < 0) {
				String bName = bookMapper.getBNameById(bId);
				return ResponseResult.ERROR(265, "请核查订单，" + (i + 1) + "个订单支付时出现了" + bName + "书籍库存不足");
			}
			//更新map
			mapTempBook.put(bId, bIdAndBookAccount);
			// 判断积分是否足够；
			// 获取用户积分
			Integer userId = UserUtil.getUserId();
			//判断是否为负数
			if (gration < 1) {
				return ResponseResult.ERROR(219, "您的余额已不足");
			}
			//书籍的价格
			Integer price = order.getBook().getBookPrice();
			gration = gration - (price * account);
			if (gration < 0) {
				return ResponseResult.ERROR(220, "您的余额不足");
			}
			BorringStatus borringStatus = new BorringStatus();
			borringStatus.setBId(order.getBook().getBId());
			borringStatus.setUserId(userId);
			borringStatus.setBorrwingStatus(true);
			// 先查看当前是否有该订单
			List<BorringStatus> lists = userOrderStatusMapper.selectBorringStatus(borringStatus);
			if (lists != null && lists.size() != 0) {
				return ResponseResult.ERROR(216, "第" + (i + 1) + "订单已经生成了，请不要重复提交哦");
			}
			borringStatus.setLoanHour(LocalDateTime.now());
				order.setDistrbutionId(distrubutionId);
				borringStatus.setSendStatus(true);

			order.setOrderStatus(true);
			order.setOrderTime(new Date());
			order.setPay(true);
			orderBench.add(order);
			borringStatusList.add(borringStatus);
		}
	}
		//更新用户积分信息
		Map<String ,Integer> mapInteger=new HashMap<>();
		mapInteger.put("integration",gration);
		mapInteger.put("id", UserUtil.getUserId());
		userMapper.updateIntegration(mapInteger);
		//更新订单信息
		orderMapper.updateOrderBench(orderBench);
		// 生成书籍用户订单信息
		userOrderStatusMapper.addStatusBench(borringStatusList);
		bookMapper.updateBookBench(mapTempBook);
		return ResponseResult.SUCCESSM("支付成功");
	}

	@Override
	public ResponseResult deleteOrder(Integer id) {
		if (Tools.isNull(id)) {
			return ResponseResult.ERROR(250,"删除的id不能为空");
		}
		//判断用户是否已经将数据删除
		Integer flag=orderMapper.getIsPay(id);
		if(Tools.isNull(flag)){
			return ResponseResult.ERROR(251,"订单已经删除了");
		}
		if (flag==1){
			return ResponseResult.ERROR(252,"已经支付的订单不能删除");
		}

		//删除订单
		orderMapper.deleteOrder(id);
		return ResponseResult.SUCCESSM("删除成功");
	}
}
