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
            return ResponseResult.ERROR(501,"需要评价的订单不能为空或者订单不合法");
        }
        //判断订单号是否为一个整数,很容易出现数字转换异常，以后统一处理
       // remarkToBook.setOrderId();Integer.valueOf();
        if (Tools.isNull(remarkToBook.getRemarkToBookContent())) {
            return ResponseResult.ERROR(502,"请输入需要评价的内容");
        }
        String content=remarkToBook.getRemarkToBookContent();

        //先从数据库中查找是否已经被评价过
        remarkToBook=remarkMapper.getRemarkToBook(remarkToBook.getOrderId());
        //没有被支付的订单是不能进行评论的
        if(Tools.isNull(remarkToBook)){
            return ResponseResult.ERROR(503,"没有对应的订单需要评价");
        }
        Integer tempOrderId=remarkMapper.getOrderId(remarkToBook.getOrderId());
        if (Tools.notNull(tempOrderId)) {
            return ResponseResult.ERROR(507,"你已经通过该订单对书籍做过评论了");
        }
        //判断是否为本人的订单
        //先设置remarkToBook 的userId
        remarkToBook.setUserId(orderMapper.getUserId(remarkToBook.getOrderId()));
        if(UserUtil.getUserId()!=remarkToBook.getUserId()){
            return ResponseResult.ERROR(504,"不是你的订单请不要随便评论");
        }
       if(orderMapper.getIsPay(remarkToBook.getOrderId())==0){
           return ResponseResult.ERROR(506,"你还没有支付该订单不能评价的哦");
       }
        remarkToBook.setRemarkToBookContent(content);
        /**
         * 通过数据库的订单id查找出用户的bId,userId
         */
        remarkMapper.addRemarkToBook(remarkToBook);
        //判断配送员是否被评论
        Integer  tmpeGrade=remarkMapper.getSendGrade(remarkToBook.getOrderId());
        //通过订单id获取配送员id
        Integer distributeId=orderMapper.getDistributeId(remarkToBook.getOrderId());
        if(Tools.notNull(tmpeGrade) || distributeId==-1){
            Map<String,Object> map=new HashMap<>();
            map.put("id",remarkToBook.getOrderId());
            map.put("orderBool",false);
            orderMapper.updateBool(map);
        }
        return ResponseResult.SUCCESSM("书籍评论成功");
    }
    @Override
    @Transactional
    public ResponseResult addToDeveliverRemark(RemarkToDeveliver remarkToDeveliver) {
        /**
         * 评论配送员
         */
        if (Tools.isNull(remarkToDeveliver.getOrderId())) {
            return ResponseResult.ERROR(511,"请输入需要评价的id");
        }
        if(Tools.isNull(remarkToDeveliver.getGradeClass())){
            return ResponseResult.ERROR(512,"请选择星级");
        }
        //传入的gradeClass,必须是数字类型，否则会抛出异常
        if(remarkToDeveliver.getGradeClass()<=0||remarkToDeveliver.getGradeClass()>5){
            return ResponseResult.ERROR(513,"星级不合法");
        }
        //这里需要加入通过订单找对应的配送员id不能为-1的用户
        Integer distributeId=orderMapper.getDistributeId(remarkToDeveliver.getOrderId());
        if(distributeId==-1){
            return ResponseResult.ERROR(517,"您属于自取用户，没有配送员可以评论的哦");
        }
        //将id设置到配送 评价对象中
        remarkToDeveliver.setDeveliverManId(distributeId);
        //操作数据库防止出现用户重复对配送员进行评论
        Integer  tmpeGrade=remarkMapper.getSendGrade(remarkToDeveliver.getOrderId());
        if(Tools.notNull(tmpeGrade)){
            return ResponseResult.SUCCESS("您已经对订单进行过评价了，请不要对配送员重复评价",tmpeGrade);
        }
        Integer grades=remarkToDeveliver.getGradeClass();
       //判断是否为本人的订单
        remarkToDeveliver.setUserId(orderMapper.getUserId(remarkToDeveliver.getOrderId()));
        if(UserUtil.getUserId()!=remarkToDeveliver.getUserId()){
            return ResponseResult.ERROR(514,"不是你的订单请不要随便评论");
        }
        if(orderMapper.getIsPay(remarkToDeveliver.getOrderId())==0){
            return ResponseResult.ERROR(515,"你还没有支付该订单不能评价的哦");
        }
       remarkToDeveliver.setGradeClass(grades);
       //不能对自己的配送的订单进行评价
       //获取当前用户的id
        if(UserUtil.getUserId()==remarkToDeveliver.getDeveliverManId()){
            return ResponseResult.ERROR(516,"您不能对自己的配送的订单进行评价的哦");
        }
        //判断订单是否被评论
       Integer tempRemarkToBook=remarkMapper.getOrderId(remarkToDeveliver.getOrderId());
        if(Tools.notNull(tempRemarkToBook)){
            Map<String,Object> mapBool=new HashMap<>();
            mapBool.put("id",remarkToDeveliver.getOrderId());
            mapBool.put("orderBool",false);
            orderMapper.updateBool(mapBool);
        }
        //获取配送员的信誉积分
        //配送员首次评论会
    Integer gradesClass=userMapper.getSendGrade(remarkToDeveliver.getDeveliverManId());
        //更新配送员的信誉积分
        gradesClass=(gradesClass+remarkToDeveliver.getGradeClass())/2;
        //更新配送员的信誉积分
        Map<String,Integer> map=new HashMap<>();
        map.put("deliveManGrade",gradesClass);
        map.put("id",remarkToDeveliver.getDeveliverManId());
        userMapper.updateGrade(map);
        remarkMapper.addRemarkToDeveliver(remarkToDeveliver);
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
            return ResponseResult.ERROR(521,"查看详情时书籍id不能为空的哦");
        }
       List<RemarkToBookCreateEntity> bookRemarks= remarkMapper.getRemarkToBookCreateEntity(bId);
        if(Tools.isNull(bookRemarks)||bookRemarks.size()==0){
            return  ResponseResult.ERROR(522,"目前该书籍还没有人评价过或者你的书籍没有你选择的书籍哦");
        }
        return ResponseResult.SUCCESS("这里包含了书籍的全部评价",bookRemarks);
    }
}
