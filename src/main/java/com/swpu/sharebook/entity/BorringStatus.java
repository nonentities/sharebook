package com.swpu.sharebook.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
    @TableField("borrwingStatus")
    private Boolean borrwingStatus;

    /**
     * 借阅时间
     */
    @TableField("loanHour")
    private LocalDateTime loanHour;

    /**
     * 归还时间
     */
    @TableField("returnTime")
    private LocalDateTime returnTime;

    /**
     * 配送状态
     */
    @TableField("sendStatus")
    private Boolean sendStatus;


}
