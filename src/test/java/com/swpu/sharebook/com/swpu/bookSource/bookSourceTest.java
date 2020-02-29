package com.swpu.sharebook.com.swpu.bookSource;

import com.swpu.sharebook.entity.BookSource;
import com.swpu.sharebook.entity.createentity.BookUserSource;
import com.swpu.sharebook.mapper.BookSourcesMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class bookSourceTest {
@Resource

private BookSourcesMapper bookSourcesMapper;
@Test
public void testUserBookSource(){
   List<BookSource> bookSourceList=bookSourcesMapper.getUserBookSource(1);
   System.out.println(bookSourceList);
}
@Test
   public void testBookUserSource(){
   List<BookUserSource> mmqList=bookSourcesMapper.getBookSource(1);
   System.out.println(mmqList);
}
}
