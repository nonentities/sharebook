package com.swpu.sharebook.mapper;

import java.util.List;

import com.swpu.sharebook.entity.BookSource;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookSourcesMapper {
public Integer addBookSources(BookSource bookSource);
/**
 * 获取审核的用户信息
 * @param boolPass
 * @return
 */
public List<BookSource> getBookSource(Integer boolPass);
//获取指定用户的id
public BookSource getABookSource(Integer sourcesId);
//更新对象
public void auditUpdateBookSource(BookSource bookSource);
/**
 * 获取当前用户的的捐赠的所有书籍
 */
public List<BookSource> getUserBookSource(Integer uId);
}
