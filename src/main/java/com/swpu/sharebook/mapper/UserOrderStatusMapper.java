package com.swpu.sharebook.mapper;

import com.swpu.sharebook.entity.BorringStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserOrderStatusMapper {
    //添加状态信息
public Integer addStatus(BorringStatus borringStatus);
//通过逐渐生成list
public BorringStatus selectBorringStatusById(Integer oId);
//更新BorringStatus
public void updateBorringStatus(BorringStatus borringStatus);
public void delete(BorringStatus borringStatus);
public void addStatusBench(@Param("listBorringStatus") List<BorringStatus> listBorringStatus);
/**
 * 获取当前配送的用户与订单中userId的订单信息
 */
/**
 * 批量更新用户订单的状态
 */
public void updateBorringStatusBench(@Param(("borringStatusList")) List<BorringStatus> borringStatusList);
}
