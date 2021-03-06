package com.swpu.sharebook.controller;

import com.swpu.sharebook.entity.UserRole;
import com.swpu.sharebook.service.RoleService;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/roleImproveController")

//下面的三个方法只能是超级管理员才能执行
public class RoleImproveController {
    @Resource
    RoleService roleService;
    @RequiresRoles({"超级管理员"})
    @GetMapping("/getDontAudientRole")
    public ResponseResult getDontAudientRole(){
        return roleService.getDontAudientRole();
    }
    @RequiresRoles({"超级管理员"})
    @PostMapping("/updateOrDelete")
    public ResponseResult updateOrDelete(Integer id,boolean flag){
        return roleService.updateOrDelete(id,flag);
    }
    @PostMapping("/upToIncreaseRole")
    public ResponseResult upToIncreaseRole(UserRole userRole){
        return roleService.upToIncreaseRole(userRole);
    }
}
