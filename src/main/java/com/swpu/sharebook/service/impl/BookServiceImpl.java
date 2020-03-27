package com.swpu.sharebook.service.impl;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.swpu.sharebook.entity.Book;
import com.swpu.sharebook.entity.BookSource;
import com.swpu.sharebook.entity.User;
import com.swpu.sharebook.entity.createentity.BookUserSource;
import com.swpu.sharebook.mapper.BookMapper;
import com.swpu.sharebook.mapper.BookSourcesMapper;
import com.swpu.sharebook.mapper.UserMapper;
import com.swpu.sharebook.shiro.util.UserUtil;
import com.swpu.sharebook.util.Tools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.swpu.sharebook.service.BookService;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
@Service
public class BookServiceImpl implements BookService {
	@Resource
	private BookMapper bookMapper;
	@Resource
	private BookSourcesMapper bookSourcesMapper;
	@Resource
	private UserMapper userMapper;
	@Override
	public ResponseResult getBookByName(String bookName) {
		Book book = bookMapper.getBookByName(this.getMap("bName", bookName));
		if (book == null) {
			return ResponseResult.ERROR(300, "没有您要搜索的书籍");
		}
		return ResponseResult.SUCCESS(book);
	}

	@Override
	public Book getBookByWriterName(String writerName) {

		return null;
	}

	@Override
	public Book getBookBySourceId(Integer sourceId) {

		System.out.println("mjdfjgj");
		return null;
	}

	@Transactional
	@Override
	public ResponseResult addBook(Book book) {
		Integer bookAccount = book.getBookAccount();
		// 判断是否有书籍
		Book bookFlag = bookMapper.getBookByName(this.getMap("bName", book.getBName()));
		if (bookFlag == null) {
			// 将书籍的数量置为0
			book.setBookAccount(0);
			bookMapper.addBook(book);
		} else {
			// 目前有该书籍ff
			book = bookFlag;
		}
		// 最后将生成对应的数据源头表
		// 新建一个bookSource
		// int a=2/0;
		BookSource bookSource = new BookSource();
		// 将当前用户的id插入到用户中从当前线程中找对应的用户

		bookSource.setUserId(UserUtil.getUserId());
		bookSource.setBook(book);
		bookSource.setSourceTime(LocalDateTime.now());
		bookSource.setBookAccount(bookAccount);
		bookSourcesMapper.addBookSources(bookSource);
		return ResponseResult.SUCCESSM("书籍添加成功等待审核书籍是否合法");
	}
	@Override
	public ResponseResult updateBook(Book book) {
		return null;
	}

	@Override
	public Integer deleteBook(Book book) {
		return null;
	}

	private Map<String, String> getMap(String column, String value) {
		Map<String, String> map = new HashMap<>();
		map.put("column", column);
		map.put("value", value);
		return map;
	}

	@Transactional
	@Override
	public ResponseResult auditBookSource(Integer sourceId) {
		Integer userId=UserUtil.getUserId();

		Map<String, Integer> map = new HashMap<>();
		// 查找当前id的书籍源头表
		BookSource bookSource = new BookSource();
		BookSource bookSource2 = bookSourcesMapper.getABookSource(sourceId);
		if (bookSource2 == null) {
			// 当前用户已经取消了当前书的捐赠
			return ResponseResult.ERROR(303, "当前用户已经取消了当前书籍的捐赠");
		}
		if (bookSource2.getBoolPass() != 0) {
			return ResponseResult.ERROR(304, "你来晚了一步，已经被审核了");
		}
		bookSource.setBoolPass(userId);
		bookSource.setSourceTime(LocalDateTime.now());
		bookSource.setId(sourceId);
		bookSourcesMapper.auditUpdateBookSource(bookSource);
		 Book book = bookMapper.getBookById(bookSource2.getBook().getBId());
		// 获取书籍的id，书籍数量+数量
		 Integer bookAccount = book.getBookAccount() + bookSource2.getBookAccount();
		 book.setBookAccount(bookAccount);
		 bookMapper.updateBook(book);
		// 审核人积分+1；
		map.put("id",userId);
		// 解决刚才问题的方案是重新获取数据库信息避免脏读
		User user = userMapper.getUserById(userId);
		// 将user设置的redis中，进行更新操作
		map.put("integration", (user.getIntegration() + bookSource2.getBookAccount()));
		userMapper.updateIntegration(map);
		Integer integration = userMapper.getIntegration(bookSource2.getUserId()) + (3 * bookSource2.getBookAccount());
		// 查询到捐赠书籍的用户的分数
		// 将用户的积分+3；
		// 这里的sql文件
		map.put("id", bookSource2.getUserId());
		map.put("integration", integration);
		userMapper.updateIntegration(map);
		return ResponseResult.SUCCESSM("审核成功");
	}

	@Override
	public ResponseResult getSourceBook() {
		// TODO Auto-generated method stub
		Integer userId=UserUtil.getUserId();
		return ResponseResult.SUCCESS(bookSourcesMapper.getUserBookSource(userId));
	}

	@Override
	public ResponseResult getSouirceBookByCurrent() {

		return null;
	}

	@Override
	public ResponseResult selectBook(Book book) {
		// 获取当前用户的数据
		List<Book> bookList = bookMapper.selectBook(book);
		return ResponseResult.SUCCESS("查询结果为", bookList);
	}

	@Override
	public ResponseResult getBookDontAndit(Integer bookBoolId) {
		// 获取order对象
		List<BookUserSource> orderList = bookSourcesMapper.getBookSource(bookBoolId);
		if (orderList == null || orderList.size() == 0) {
			return ResponseResult.SUCCESSM("没有审核数据");
		}
		// 循环遍历出来，并操作
		// 创建一个对象：Map
		/*Map<BookSource, Book> mapBook = new HashMap<>();
		for (int i = 0; i < orderList.size(); i++) {
			Book bookTemp = null;
			// bookTemp.setbId(orderList.get(i).getbId());
			// bookTemp=bookMapper.getBookById(orderList.get(i).getbId());
			// if(bookTemp==null) {
			// return 未知错误,这个一般不会出现
			// }
			// 将数据放入到map中
			mapBook.put(orderList.get(i), bookTemp);
		}*/
		return ResponseResult.SUCCESS(orderList);
	}

	@Override
	public ResponseResult getBookByKey(String key) {

		// 如果没有关键字就查看所有
		if (Tools.isNull(key)) {
			Book book = new Book();
			List<Book> bookList = bookMapper.selectBook(book);
			return ResponseResult.SUCCESS("查询结果为", bookList);
		}
	//	List<List<Book>> lists = new ArrayList<>();
		List<Book> nowList=null;
		// 通过名字找到书籍
		Book book = new Book();
		// 创建一个List 保存到Lists中
		// 第一步通过名字查找
		book.setBName(key);
		List list = bookMapper.getBookByKey(book);
		if (list != null) {
			nowList=list;
		}
		// 将书名置空
		book.setBName(null);
		// 第二部通过关键字书籍作者
		book.setIntroduction(key);
		list = bookMapper.getBookByKey(book);
		if (list != null) {
			nowList=destroyRepeat(list,nowList);
		}
		// 将书籍简介置空；
		book.setIntroduction(null);
		// 第三步通过书籍作者查询
		book.setWriter(key);
		list = bookMapper.getBookByKey(book);
		if (list != null) {
			nowList=destroyRepeat(list,nowList);
		}
		// 将作者置空
		book.setWriter(null);
		// 第四步通过出版社查询
		book.setBPublish(key);
		list = bookMapper.getBookByKey(book);
		if (list != null) {
			nowList=destroyRepeat(list,nowList);
		}
		// 最后一步不用置为空，因为不需要继续处理数据了
		if (nowList.size() == 0) {
			return ResponseResult.ERROR(311, "没有您相要的书籍，请使用其他关键字搜索");
		}
		// 不为空将数据返回给客户端
		return ResponseResult.SUCCESS(nowList);
	}

	@Override
	public ResponseResult getSendOrder() {
		// 获取用户的配送员的信息
		List<User> sendsUsers = userMapper.sendUsers();
		return ResponseResult.SUCCESS(sendsUsers);
	}

	@Override
	public ResponseResult updateBookPrice(Integer id, Integer price) {
		if(Tools.isNull(id)){
			return ResponseResult.ERROR(340,"书籍id不能为空");
		}
		if(Tools.isNull(price)){
			return ResponseResult.ERROR(341,"书籍价格不能为空");
		}
		if(price>=1&&price<=5){
			return ResponseResult.ERROR(342,"书籍价格必须介于1到5之间");
		}
		//更新用户的书籍
		Map<String ,Object> map=new HashMap<>();
		map.put("bId",id);
		map.put("bookPrice",price);
		bookMapper.updateBookPrice(map);
		return ResponseResult.SUCCESSM("书籍价格修改成功");
	}
	/**
	书籍去重复的功能
	 */
	public List<Book> destroyRepeat(List<Book> previousList,List<Book> nowList){
		//需要把现在list长度保存
		int nowLenth=nowList.size();
		for(int i=0;i<previousList.size();i++){
			int j=0;
			Book preBook=previousList.get(i);
			for(j=0;j<nowLenth;j++){
				Book nowBook=nowList.get(j);
				if(preBook.getBId()==nowBook.getBId()){
					break;
				}
			}
			//判断如果达到了最后一个说明没有对应的数据，就应该加载到lis里面
			if(j==nowLenth){
				nowList.add(preBook);
			}
		}
		return nowList;
	}
}
