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
public List<BorringStatus> selectBorringStatus(BorringStatus borringStatus);
//更新BorringStatus
public void updateBorringStatus(BorringStatus borringStatus);
public void delete(BorringStatus borringStatus);
public void addStatusBench(@Param("listBorringStatus") List<BorringStatus> listBorringStatus);
}
