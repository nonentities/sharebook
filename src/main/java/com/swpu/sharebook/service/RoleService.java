package com.swpu.sharebook.service;

import com.swpu.sharebook.entity.UserRole;
import com.swpu.sharebook.util.returnvalue.ResponseResult;

public interface RoleService {
    /**
     * 获取未被升级的用户
     * @return
     */
    public ResponseResult getDontAudientRole();
    /**
     * 新增用户的信息添加信息
     * @1需要新增用户角色字段
     * @2需要将用户的数据置为0
     * @2直接修改用户申请的角色信息
     */
    public ResponseResult updateOrDelete(Integer id, boolean flag);
    /**
     * @用户向系统申请提升角色
     */
    public ResponseResult upToIncreaseRole(UserRole userRole);
}
