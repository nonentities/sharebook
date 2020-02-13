package com.swpu.sharebook.entity;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class RemarkToDeveliver {
    private Integer orderId;
    private Integer userId;
    private Integer develiverManId;
    private Integer gradeClass;
 //   private String
}
