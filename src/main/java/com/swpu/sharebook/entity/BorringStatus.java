package com.swpu.sharebook.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2020-01-26
 */
@Data
@TableName("borringstatus")
public class BorringStatus implements Serializable {

    private static final long serialVersionUID=1L;
    private Integer oId;
    /**
     * 书籍id
     */
    @TableId("bId")
    private Integer bId;
    /**
     * 用户id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 用户借阅状态
     */
    private Boolean borrwingStatus;

    /**
     * 借阅时间
     */
    @TableField("loanHour")
    private Date loanHour;

    /**
     * 归还时间
     */
    @TableField("returnTime")
    private Date returnTime;

    /**
     * 配送状态
     */
    @TableField("sendStatus")
    private Boolean sendStatus;
}
