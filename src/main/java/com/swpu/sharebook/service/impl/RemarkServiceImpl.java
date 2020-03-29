package com.swpu.sharebook.service.impl;

import com.swpu.sharebook.entity.RemarkToBook;
import com.swpu.sharebook.entity.RemarkToDeveliver;
import com.swpu.sharebook.entity.createentity.RemarkToBookCreateEntity;
import com.swpu.sharebook.mapper.OrderMapper;
import com.swpu.sharebook.mapper.RemarkMapper;
import com.swpu.sharebook.mapper.UserMapper;
import com.swpu.sharebook.service.RemarkService;
import com.swpu.sharebook.shiro.util.UserUtil;
import com.swpu.sharebook.util.Tools;
import com.swpu.sharebook.util.returnvalue.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 这里我会专门写两个方法查询bId，查询userId即可
 */
@Service
public class RemarkServiceImpl implements RemarkService {
    @Resource
private RemarkMapper remarkMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private OrderMapper orderMapper;
    @Transactional
    public ResponseResult addToBookRemark(RemarkToBook remarkToBook) {
        if(Tools.isNull(remarkToBook.getOrderId())){
            return ResponseResult.ERROR(800,"需要评价的订单不能为空或者订单不合法");
        }
        //判断订单号是否为一个整数,很容易出现数字转换异常，以后统一处理
       // remarkToBook.setOrderId();Integer.valueOf();
        if (Tools.isNull(remarkToBook.getRemarkToBookContent())) {
            return ResponseResult.ERROR(801,"请输入需要评价的内容");
        }
        String content=remarkToBook.getRemarkToBookContent();

        //先从数据库中查找是否已经被评价过
        remarkToBook=remarkMapper.getRemarkToBook(remarkToBook.getOrderId());
        //没有被支付的订单是不能进行评论的
        if(remarkToBook==null){
            return ResponseResult.ERROR(802,"没有对应的订单需要评价");
        }
        //判断是否为本人的订单
        if(UserUtil.getUserId()!=remarkToBook.getUserId()){
            return ResponseResult.ERROR(819,"不是你的订单请不要随便评论");
        }
       if(orderMapper.getIsPay(remarkToBook.getOrderId())==0){
           return ResponseResult.ERROR(804,"你还没有支付该订单不能评价的哦");
       }
       if(orderMapper.getOrderStatus(remarkToBook.getOrderId())==0){
           return ResponseResult.ERROR(803,"该订单已经被你取消了，就不要去评价了哦");
       }
        remarkToBook.setRemarkToBookContent(content);
        /**
         * 通过数据库的订单id查找出用户的bId,userId
         */
        remarkMapper.addRemarkToBook(remarkToBook);
        Map<String,Object> map=new HashMap<>();
        map.put("id",remarkToBook.getOrderId());
        map.put("orderBool",false);
        orderMapper.updateBool(map);
        return ResponseResult.SUCCESSM("书籍评论成功");
    }
    @Override
    @Transactional
    public ResponseResult addToDeveliverRemark(RemarkToDeveliver remarkToDeveliver) {
        /**
         * 评论配送员
         */
        if (Tools.isNull(remarkToDeveliver.getOrderId())) {
            return ResponseResult.ERROR(813,"请输入需要评价的id");
        }
        if(Tools.isNull(remarkToDeveliver.getGradeClass())){
            return ResponseResult.ERROR(814,"请选择星级");
        }
        //传入的gradeClass,必须是数字类型，否则会抛出异常
        if(remarkToDeveliver.getGradeClass()<=0||remarkToDeveliver.getGradeClass()>5){
            return ResponseResult.ERROR(815,"星级不合法");
        }
        //这里需要加入通过订单找对应的配送员id不能为-1的用户
        //操作数据库防止出现用户重复对配送员进行评论
        Integer  tmpeGrade=remarkMapper.getSendGrade(remarkToDeveliver.getOrderId());
        if(Tools.notNull(tmpeGrade)){
            return ResponseResult.SUCCESS("您已经对订单进行过评价了，请不要对配送员重复评价",tmpeGrade);
        }
        Integer grades=remarkToDeveliver.getGradeClass();
       remarkToDeveliver= remarkMapper.getRemarkToDeveliver(remarkToDeveliver.getOrderId());
       if(Tools.isNull(remarkToDeveliver)) {
           return ResponseResult.ERROR(816, "没有您选择的订单");
       }
       //判断是否为本人的订单
        if(UserUtil.getUserId()!=remarkToDeveliver.getUserId()){
            return ResponseResult.ERROR(819,"不是你的订单请不要随便评论");
        }
        if(orderMapper.getIsPay(remarkToDeveliver.getOrderId())==0){
            return ResponseResult.ERROR(817,"你还没有支付该订单不能评价的哦");
        }
        if(orderMapper.getOrderStatus(remarkToDeveliver.getOrderId())==0){
            return ResponseResult.ERROR(818,"该订单已经被你取消了，就不要去评价了哦");
        }

       remarkToDeveliver.setGradeClass(grades);
       //不能对自己的配送的订单进行评价
       //获取当前用户的id
        if(UserUtil.getUserId()==remarkToDeveliver.getDeveliverManId()){
            return ResponseResult.ERROR(818,"您不能对自己的配送的订单进行评价的哦");
        }

        //获取配送员的信誉积分
    Integer gradesClass=userMapper.getSendGrade(remarkToDeveliver.getDeveliverManId());
        //更新配送员的信誉积分
        gradesClass=(gradesClass+remarkToDeveliver.getGradeClass())/2;
        //更新配送员的信誉积分
        Map<String,Integer> map=new HashMap<>();
        map.put("deliveManGrade",gradesClass);
        map.put("id",remarkToDeveliver.getDeveliverManId());
        userMapper.updateGrade(map);
        remarkMapper.addRemarkToDeveliver(remarkToDeveliver);
        Map<String,Object> mapBool=new HashMap<>();
        mapBool.put("id",remarkToDeveliver.getOrderId());
        mapBool.put("orderBool",false);
        orderMapper.updateBool(mapBool);
        return ResponseResult.SUCCESSM("评论成功");
    }
    /**
     * 目前不急着写
     * @return
     */
    @Override
    public ResponseResult selectDeveliverRemark() {
        return null;
    }

    @Override
    public ResponseResult selectRemarkToBook(Integer bId) {
        if(Tools.isNull(bId)){
            return ResponseResult.ERROR(820,"查看详情时书籍id不能为空的哦");
        }
       List<RemarkToBookCreateEntity> bookRemarks= remarkMapper.getRemarkToBookCreateEntity(bId);
        if(Tools.isNull(bookRemarks)||bookRemarks.size()==0){
            return  ResponseResult.ERROR(821,"目前该书籍还没有人评价过或者你的书籍没有你选择的书籍哦");
        }
        return ResponseResult.SUCCESS("这里包含了书籍的全部评价",bookRemarks);
    }
}
