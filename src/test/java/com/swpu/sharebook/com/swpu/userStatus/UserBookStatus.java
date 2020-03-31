package com.swpu.sharebook.com.swpu.userStatus;

import com.swpu.sharebook.entity.BorringStatus;
import com.swpu.sharebook.mapper.UserOrderStatusMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class UserBookStatus {
    @Resource
    private UserOrderStatusMapper userOrderStatusMapper;
    @Test
    public void testInsertBench(){
        List<BorringStatus> list=new ArrayList<>();

        for(int i=0;i<10;i++){
            BorringStatus borringStatus=new BorringStatus();
            borringStatus.setUserId(i);
            borringStatus.setBId(i);
            borringStatus.setSendStatus(true);
            borringStatus.setLoanHour(new java.util.Date());
            list.add(borringStatus);
        }
        userOrderStatusMapper.addStatusBench(list);
    }
    @Test
    public void testUpdateSend(){
        BorringStatus borringStatus=new BorringStatus();
        borringStatus.setLoanHour(new java.util.Date());
        borringStatus.setBId(3);
        borringStatus.setUserId(1);
        BorringStatus borringStatus1=new BorringStatus();
        borringStatus1.setLoanHour(new Date());
        borringStatus1.setBId(6);
        borringStatus1.setUserId(1);
        BorringStatus borringStatus2=new BorringStatus();
        borringStatus2.setLoanHour(new java.util.Date());
        borringStatus2.setBId(7);
        borringStatus2.setUserId(1);
        List<BorringStatus>list=new ArrayList<>();
        list.add(borringStatus);
        list.add(borringStatus1);
        list.add(borringStatus2);
        userOrderStatusMapper.updateBorringStatusBench(list);
    }
}
