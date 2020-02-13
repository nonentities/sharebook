package com.swpu.sharebook.service;

import com.swpu.sharebook.entity.RemarkToBook;
import com.swpu.sharebook.util.returnvalue.ResponseResult;

public interface RemarkService {
    public ResponseResult addToBookRemark(RemarkToBook remarkToBook);;
    public ResponseResult addToDeveliverRemark();
    public ResponseResult selectBookRemark();
    public ResponseResult selectDeveliverRemark();
}
