package com.swpu.sharebook.mapper;
import java.util.List;
import java.util.Map;

import com.swpu.sharebook.entity.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {
	 Book getBookByName(Map<String, String> map);
	//下面的以后应该用Map来接收
	 List<Book> getBookByWriterName(Map<String, String> map);
	 Integer getBookByNameReturnInt(Map<String, String> map);
//	 List<Book> getBookBySourceId(Integer sourceId);
	 Integer addBook(Book book);
	 Integer updateBook(Book updateBook);
//	 Integer deleteBook(Book updateBook);
	 Book getBookById(Integer bId);
	 List<Book> selectBook(Book book);
	/**
	 * 通过任意的书籍id获取任意整形
	 */
	 Integer getBookReturnInt(Map<String, Object> map);
	//通过关键字搜索书籍
	 List<Book> getBookByKey(Book book);
}
