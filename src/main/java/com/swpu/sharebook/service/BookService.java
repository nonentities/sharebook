package com.swpu.sharebook.service;

import java.util.List;

import com.swpu.sharebook.entity.Book;
import com.swpu.sharebook.util.returnvalue.ResponseResult;

public interface BookService {
	/**
	 * 通过书名获取书籍
	 * @getBookByName
	 */
public ResponseResult getBookByName(String bookName);
/**
 * 通过作者获取书名
 * @getBookByWriterName
 * @param writerName
 * @return
 */
public Book getBookByWriterName(String writerName);
/**
 * 通过书籍赠送源头获取书籍
 * @getBookBySourceId
 * @param sourceId
 * @return
 */
public Book getBookBySourceId(Integer sourceId);
/**
 * 更新书籍
 */
/**
 * @addBook
 * 添加书籍
 * 添加书籍分为两步？
<<<<<<< HEAD
 * @1 普通用户添加: 书籍源表：是否包含该书籍：通过书名查找，如果没有该书籍：添加书籍:并返回书籍id;
 * @2 :将书籍源头信息插入到书籍源表中，此时为没有通过审核：时间动态插入 @1 2为一个事务
 * 管理员添加书籍：审核信息（查询没有被审核的书籍），不合法删除对应的字段信息： 合法：将对应的书籍+1，并且将对应的源书表设置为审核：
 * 然后将对应的用户积分值+3；系统也会对该管理员进行加0.5的操作;
=======
 * @1 普通用户添加: 书籍源表：是否包含该书籍：通过书名查找，如果没有该书籍：添加书籍:并返回书籍id，
 * @2 :将书记源头信息插入到书籍源表中，此时为没有通过审核：时间动态插入 @1 2为一个事务
 * 管理员添加书籍：审核信息（查询没有被审核的书籍），不合法删除对应的字段信息： 合法：将对应的书籍+1，并且将对应的源书表设置为审核：
 * 然后将对应的用户积分值+3；系统也会对该管理员进行加0.5的操作；
>>>>>>> branch 'master' of https://gitee.com/KeennessNewBie/BookCompetition
 * 以上为一个事务
 */
public ResponseResult addBook(Book book);
/**
 * 修改书籍
 * @updateBook
 * 后期需要修改
 */
public ResponseResult updateBook(Book book);
/**
 * @deleteBook
 * 通过用户名删除
 */
public Integer deleteBook(Book book);
/**
 * 审核用户信息，这里涉及到线程同步问题，优化时再做处理
 * @param sourceId 
 * @auditBookSource
 * 业务要求
 * 前端要求，需要传入一个当前审核的用户的数据的id，审核书籍的id进来，后端查找并处理
 * @1查看所有的未审核的信息？这里优化时涉及到一些同步问题？
 * @自动修改书记源的boolpass  ,修改当前用户的时间，修改书籍的数量，用户积分+3，审核人的积分+1；
 * @return
 */
public ResponseResult auditBookSource(Integer sourceId);
/**
 * @getSourceBook
 * 获取书籍源表的的所有信息这里只有管理员能够查看
 * 包含被审核还是未被审核的都能看
 * @return
 */
public ResponseResult getSourceBook(Integer sourceId);
/**
 * 获取当前用户捐赠的书籍
 * @return
 * @getSouirceBookByCurrent
 */
public ResponseResult getSouirceBookByCurrent();
/**
 * 获取书籍信息
 * 这里可以通过 书籍名称查看书籍
 * 书籍出版社查看书籍
 * 书籍作者查看书籍
 * @param book
 * @return
 */
public ResponseResult selectBook(Book book);
/**
 * 獲取未被審核的信息
 * @param bookBoolId
 * @return
 */
public ResponseResult getBookDontAndit(Integer bookBoolId);
/**
 * 通过关键字搜索书籍
 * 通过每次的输入将数据导入到List中
 * @param key
 * @return
 */
public ResponseResult getBookByKey(String key);
/**
 * 按照积分排序获得配送员
 * @return
 */

public ResponseResult getSendOrder();


}
