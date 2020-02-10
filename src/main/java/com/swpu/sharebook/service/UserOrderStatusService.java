package com.swpu.sharebook.service;
import com.swpu.sharebook.entity.BorringStatus;
import com.swpu.sharebook.util.returnvalue.ResponseResult;

/**
 * @1其中包含用户的通过uid查看对应的订单信息列表
 * @2插入订单信息
 * @3更新订单信息，这里更新订单信息数据
 * @author mmq
 *
 */
public interface UserOrderStatusService {
	//添加状态信息
public ResponseResult addStatus(BorringStatus borringStatus);
//更新状态信息
public ResponseResult upStatus(BorringStatus borringStatus);
}
