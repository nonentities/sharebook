package com.swpu.sharebook.service.impl;

import com.swpu.sharebook.entity.RemarkToBook;
import com.swpu.sharebook.entity.RemarkToDeveliver;
import com.swpu.sharebook.service.RemarkService;
import com.swpu.sharebook.util.Tools;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * 这里我会专门写两个方法查询bId，查询userId即可
 */
@Service
public class RemarkServiceImpl implements RemarkService {

    @Override
    public ResponseResult addToBookRemark(RemarkToBook remarkToBook) {
        if(Tools.isNull(remarkToBook.getOrderId())){
            return ResponseResult.ERROR(800,"需要评价的订单不能为空或者订单不合法");
        }
        //判断订单号是否为一个整数,很容易出现数字转换异常，以后统一处理
       // remarkToBook.setOrderId();Integer.valueOf();
        if (Tools.isNull(remarkToBook.getRemarkToBookContent())) {
            return ResponseResult.ERROR(801,"请输入需要评价的内容");
        }
        //操作数据库
        return null;
    }

    @Override
    public ResponseResult addToDeveliverRemark(RemarkToDeveliver remarkToDeveliver) {
        return null;
    }

    @Override
    public ResponseResult selectBookRemark() {
        return null;
    }

    @Override
    public ResponseResult selectDeveliverRemark() {
        return null;
    }
}
