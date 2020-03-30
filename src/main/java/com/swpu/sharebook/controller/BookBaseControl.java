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
import com.swpu.sharebook.entity.Order;
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

    @GetMapping("auditBookSource")
    public ResponseResult auditBookSource(Integer sourceId) {
        return bookService.auditBookSource(sourceId);
    }

    @GetMapping("getSourceBook")
    public ResponseResult getSourceBook() {
        return bookService.getSourceBook();
    }

    @GetMapping("getSouirceBookByCurrent")
    public ResponseResult getSouirceBookByCurrent() {
        return bookService.getSouirceBookByCurrent();
    }

    @GetMapping("getBookByKey")
    public ResponseResult getBookByKey(String key) {
        return bookService.getBookByKey(key);
    }

    @GetMapping("getSendOrder")
    public ResponseResult getSendOrder() {
        return bookService.getSendOrder();
    }

    @PostMapping("updateBookPrice")
    public ResponseResult updateBookPrice(Integer id, Integer price) {
        return bookService.updateBookPrice(id, price);
    }
    @PostMapping("updateBook")
    public ResponseResult updateBook(Book book){
        return bookService.updateBook(book);
    }
}
