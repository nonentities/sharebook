package com.swpu.sharebook.service;

import com.swpu.sharebook.entity.RemarkToBook;
import com.swpu.sharebook.entity.RemarkToDeveliver;
import com.swpu.sharebook.util.returnvalue.ResponseResult;

public interface RemarkService {
    /**
     * 用户对于书籍的评价
     * @param remarkToBook
     * @return
     */
    public ResponseResult addToBookRemark(RemarkToBook remarkToBook);;
    public ResponseResult addToDeveliverRemark(RemarkToDeveliver remarkToDeveliver);
    public ResponseResult selectDeveliverRemark();
    public ResponseResult selectRemarkToBook(Integer bId);
}
