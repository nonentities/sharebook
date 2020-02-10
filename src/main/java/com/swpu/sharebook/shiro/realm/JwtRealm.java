package com.swpu.sharebook.shiro.realm;

import com.swpu.sharebook.entity.Role;
import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.mapper.RoleMapper;
import com.swpu.sharebook.mapper.UserMapper;
import com.swpu.sharebook.shiro.jwt.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @author KeennessNewBie
 * @Date 2020/1/23 10:55
 * @Message
 */
@Slf4j

public class JwtRealm extends AuthorizingRealm {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("开始认证....");

        JwtToken jwtToken = (JwtToken) token;
        String info = jwtToken.getPrincipal().toString();
        log.info("认证完成....");
        return new SimpleAuthenticationInfo(info, Boolean.TRUE, getName());
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("开始授权....");
        String info = principals.getPrimaryPrincipal().toString();
        int id = Integer.valueOf(info);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = userMapper.selectById(id);
       // Role role = roleMapper.selectById(user.getRole().getRId());
       // authorizationInfo.addRole(role.getRoleName());
    //List<Role> roles=roleMapper.selectList()
        List<Role> roles=roleMapper.getRoleListByUserId(id);
        //将用户的所有角色放进去
        for(int i=0;i<roles.size();i++){
            authorizationInfo.addRole(roles.get(i).getRoleName());
        }
        log.info("授权完成....");
        return authorizationInfo;
    }
}
