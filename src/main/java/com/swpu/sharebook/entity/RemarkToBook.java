package com.swpu.sharebook.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RemarkToBook implements Serializable {
    private Integer orderId;
    private Integer userId;
    private Integer bId;
    private String remarkToBookContent;
}

