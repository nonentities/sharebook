package com.swpu.sharebook.mapper;

import com.swpu.sharebook.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2020-01-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    public User getUserById(Integer id);
    public int regester(User user);
    public int update(User user);
    public User getUserByNameAndPssword(Map<Object,Object> map);
//    public User getUserByNameAndPassword2(String userName,String password);
    public void updateIntegration(Map<String,Integer> map);
    public Integer getIntegration(Integer id);
    public List<User> sendUsers();
//    User selectById(int id);
}
