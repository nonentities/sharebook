package com.swpu.sharebook.controller;
import javax.annotation.Resource;
import javax.validation.Valid;
import com.swpu.sharebook.util.Tools;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swpu.sharebook.entity.Book;
import com.swpu.sharebook.service.BookService;

@RestController
@RequestMapping("/bookBaseControl")
//拦截只需要拦截bookBaseControl即可拦截bookcontrol下面的所有请求
public class BookBaseControl {
    @Resource
    private BookService bookService;
    @GetMapping("selectBook")
    public ResponseResult selectBook(Book book) {

        return bookService.selectBook(book);
    }

    public ResponseResult getBook(Book book) {
        return bookService.addBook(book);
    }
//仅仅管理员和超级用户拥有该方法的执行权限
    @GetMapping("getBookDontAudit")
    public ResponseResult getBookDontAudit(Integer bookBoolId) {
        return bookService.getBookDontAndit(bookBoolId);
    }

    @PostMapping("addBook")
    public ResponseResult addBook(@Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Tools.saveEorrorMessage(bindingResult);
        }
        return bookService.addBook(book);
    }
    //仅仅管理员和超级用户拥有该方法的执行权限
    @GetMapping("auditBookSource")
    public ResponseResult auditBookSource(Integer sourceId) {
        return bookService.auditBookSource(sourceId);
    }

    @GetMapping("getSourceBook")
    public ResponseResult getSourceBook() {
        return bookService.getSourceBook();
    }
//该方法目前没用
    @GetMapping("getSouirceBookByCurrent")
    public ResponseResult getSouirceBookByCurrent() {
        return bookService.getSouirceBookByCurrent();
    }
//通过关键字搜索
    @GetMapping("getBookByKey")
    public ResponseResult getBookByKey(String key) {
        return bookService.getBookByKey(key);
    }
//获取配送的订单 仅仅配送员可以执行该方法
    @GetMapping("getSendOrder")
    public ResponseResult getSendOrder() {
        return bookService.getSendOrder();
    }
//只有超级超级管理员能够执行该方法
    @PostMapping("updateBookPrice")
    public ResponseResult updateBookPrice(Integer id, Integer price) {
        return bookService.updateBookPrice(id, price);
    }
    //只有超级管理员能够执行该方法
    @PostMapping("updateBook")
    public ResponseResult updateBook(Book book){
        return bookService.updateBook(book);
    }
}
