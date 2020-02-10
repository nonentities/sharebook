package com.swpu.sharebook.com.swpu.com.swpu.sharebook.testRole;

import com.swpu.sharebook.entity.Role;
import com.swpu.sharebook.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class TestRole {
    @Resource
    private RoleMapper roleMapper;
    @Test
    public void testRele(){
        List<Role> roles=roleMapper.getRoleListByUserId(2);
        System.out.println(roles);
    }
}
