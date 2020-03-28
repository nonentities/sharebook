package com.swpu.sharebook.controller;

import com.swpu.sharebook.entity.UserRole;
import com.swpu.sharebook.service.RoleService;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/roleImproveController")
public class RoleImproveController {
    @Resource
    RoleService roleService;
    @GetMapping("/getDontAudientRole")
    public ResponseResult getDontAudientRole(){
        return roleService.getDontAudientRole();
    }
    @PostMapping("/updateOrDelete")
    public ResponseResult updateOrDelete(Integer id,boolean flag){
        return roleService.updateOrDelete(id,flag);
    }
    @PostMapping("/upToIncreaseRole")
    public ResponseResult upToIncreaseRole(UserRole userRole){
        return roleService.upToIncreaseRole(userRole);
    }
}
