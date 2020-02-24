package com.swpu.sharebook.controller;

import com.swpu.sharebook.entity.RemarkToBook;
import com.swpu.sharebook.entity.RemarkToDeveliver;
import com.swpu.sharebook.service.RemarkService;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@PostMapping("addToBookRemarkToBook")
public ResponseResult addToBookRemark( RemarkToBook remarkToBook){

    return remarkService.addToBookRemark(remarkToBook);
}
@PostMapping("addToDeveliverRemark")
public ResponseResult addToDeveliverRemark(RemarkToDeveliver remarkToDeveliver){
    return remarkService.addToDeveliverRemark(remarkToDeveliver);
}
/**
     * 查询书籍的评价信息
     * @return
     */
//    public ResponseResult selectBookRemark(Integer bId){
//
//     return remarkService.selectBookRemark(bId);
//}

/**
 * 获取书籍评价详情
 */
@GetMapping("selectRemarkToBook")
public ResponseResult selectRemarkToBook(Integer bId){
    return remarkService.selectRemarkToBook(bId);
}
}
