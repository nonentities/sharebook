package com.swpu.sharebook.com.swpu.userStatus;

import com.swpu.sharebook.entity.BorringStatus;
import com.swpu.sharebook.mapper.UserOrderStatusMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            borringStatus.setLoanHour(LocalDateTime.now());
            list.add(borringStatus);
        }
        userOrderStatusMapper.addStatusBench(list);
    }
}
