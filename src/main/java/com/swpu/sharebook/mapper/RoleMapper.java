package com.swpu.sharebook.mapper;

import com.swpu.sharebook.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swpu.sharebook.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2020-01-26
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getRoleListByUserId(Integer uId);

    // List<UserNameAndRoleName> getUserNameAndRoleName();
    void addUserRole(UserRole userRole);

    void alertTid(UserRole userRole);

    List<UserRole> getRoleListByIdAndUserId(UserRole userRole);

    UserRole getUserRoleById(Integer id);
}
