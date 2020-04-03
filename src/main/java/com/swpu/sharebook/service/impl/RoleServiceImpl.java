package com.swpu.sharebook.service.impl;

import com.swpu.sharebook.entity.UserRole;
import com.swpu.sharebook.mapper.RoleMapper;
import com.swpu.sharebook.service.RoleService;
import com.swpu.sharebook.shiro.util.UserUtil;
import com.swpu.sharebook.util.Tools;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Override
    public ResponseResult getDontAudientRole() {
        //1:获取用户的数据
        List<UserRole>  userNameAndRoleNames=roleMapper.getUserNameAndRoleName();
       return ResponseResult.SUCCESS(userNameAndRoleNames);
    }
    @Override
    public ResponseResult updateOrDelete(Integer id, boolean flag) {
        if(Tools.isNull(id)){
            return ResponseResult.ERROR(601,"用户升级的角色不能为空");
        }
      /*  if(Tools.isNull(userRole.getUId())){
            return ResponseResult.ERROR(402,"用户id不能为空");
        }
        if(Tools.isNull(userRole.getId())){
            return ResponseResult.ERROR(408,"修改的用户角色id不能为空");
        }*/
      UserRole userRole=roleMapper.getUserRoleById(id);
        if (Tools.isNull(userRole)) {
            return ResponseResult.ERROR(602, "没有对应的用户申请授权");
        }
        if(userRole.getTId()==0){
            return ResponseResult.ERROR(603,"已经升级完成了");
        }
        int temp=userRole.getTId();
        //将用户升级的tid置为0
        userRole.setTId(0);
        roleMapper.alertTid(userRole);
            if (flag) {
                //增加用户字段
                userRole.setDate(new Date());
                userRole.setTId(temp);
                roleMapper.addUserRole(userRole);
                return ResponseResult.SUCCESSM("权限升级成功");
            }
        return ResponseResult.SUCCESSM("拒绝成功");
    }
    @Override
    public ResponseResult upToIncreaseRole(UserRole userRole) {
        //用户申请
        if(Tools.isNull(userRole.getId())){
            return ResponseResult.ERROR(611,"id不能为空");
        }

        if(Tools.isNull(userRole.getTId())){
            return ResponseResult.ERROR(612,"角色id是必填项");
        }
        //包含了需要更新的角色就不能进行申请
        //查询该用户是否有该角色
        //当前用户i设置进去
        if(userRole.getTId()>=4){
            return ResponseResult.ERROR(613,"超出您所能够申请的角色范围了");
        }
        Integer uId=UserUtil.getUserId();
        userRole.setUId(uId);
       List<UserRole> userRole1s= roleMapper.getRoleListByIdAndUserId(userRole);

/*        if (Tools.isNull(userRole)) {
            return ResponseResult.ERROR(407,"您目前没有任何角色");
        }*/
//需要设置一个标志里面是否包含了对应id的值
        boolean flag=false;
        int temp=1;
        for(int i=0;i<userRole1s.size();i++){
            //求目前用户的最大值
            if(userRole1s.get(i).getRId()==userRole.getTId()){
                return ResponseResult.ERROR(614,"您已经成为了这个角色，请不要重复操作");
            }
            if(userRole1s.get(i).getRId()>temp){
                temp=userRole1s.get(i).getRId();
            }
            if(userRole1s.get(i).getTId()==userRole.getTId()){
                return ResponseResult.ERROR(615,"您已经申请过了，请等待管理员审核");
            }
            if(userRole1s.get(i).getId()==userRole.getId()){
                flag=true;
            }
        }
        //
        if(!flag){
            return  ResponseResult.ERROR(616,"请不要借助他人的账号随意申请角色");
        }
        //最后两部是
        if(temp<userRole.getTId()&&(temp+1)==userRole.getTId()) {
            roleMapper.alertTid(userRole);
            return ResponseResult.SUCCESSM("等待管理员审核");
        }else{
            return ResponseResult.ERROR(617,"您的选择误，请重新操作");
        }
    }
}
