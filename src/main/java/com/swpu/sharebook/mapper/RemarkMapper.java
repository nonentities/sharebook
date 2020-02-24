package com.swpu.sharebook.mapper;

import com.swpu.sharebook.entity.RemarkToBook;
import com.swpu.sharebook.entity.RemarkToDeveliver;
import com.swpu.sharebook.entity.createentity.RemarkToBookCreateEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RemarkMapper {
    public RemarkToBook getRemarkToBook(Integer orderId);
    public RemarkToDeveliver getRemarkToDeveliver(Integer orderId);
    public Integer addRemarkToBook(RemarkToBook remarkToBook);
    public Integer addRemarkToDeveliver(RemarkToDeveliver remarkToDeveliver);
    public List<RemarkToBookCreateEntity> getRemarkToBookCreateEntity(Integer bId);
    public Integer getSendGrade(Integer orderId);
}
