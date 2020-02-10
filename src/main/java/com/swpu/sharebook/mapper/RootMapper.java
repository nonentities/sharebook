package com.swpu.sharebook.mapper;

import java.util.List;

import com.swpu.sharebook.entity.createentity.RootAndAuthority;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RootMapper {
public List<RootAndAuthority> getAuthorityByRoleId(Integer id);
}
