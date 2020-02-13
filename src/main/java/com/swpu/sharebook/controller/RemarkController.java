package com.swpu.sharebook.controller;

import com.swpu.sharebook.entity.RemarkToBook;
import com.swpu.sharebook.entity.RemarkToDeveliver;
import com.swpu.sharebook.service.RemarkService;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/remarkController")
public class RemarkController {
/**
 * @author mmq
 * @1 添加评论：1对书籍添加评论，2对订单添加评论
 * @2 查看评论：：查看书籍评论，2查看配送员评论
 * @3 修改评论（目前不会涉及到）
 */
@Resource
private RemarkService remarkService;
public ResponseResult addToBookRemark(@Valid RemarkToBook remarkToBook){

    return remarkService.addToBookRemark(remarkToBook);
}
public ResponseResult addToDeveliverRemark(@Valid RemarkToDeveliver remarkToDeveliver){
    return remarkService.addToDeveliverRemark(remarkToDeveliver);
}
    public ResponseResult selectBookRemark(){
    return null;
}
    /**
     * 查询书籍的评价信息
     * @return
     */
    public ResponseResult selectDeveliverRemark(){
    return null;
}
}
