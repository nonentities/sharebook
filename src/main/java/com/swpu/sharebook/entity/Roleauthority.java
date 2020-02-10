package com.swpu.sharebook.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
public class Roleauthority implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 外键
     */
    @TableId("roleId")
    private Integer roleId;

    /**
     * 外键
     */
    @TableField("authorityId")
    private Integer authorityId;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private LocalDateTime createTime;

    /**
     * 取消时间
     */
    @TableField("cancelTime")
    private LocalDateTime cancelTime;
}
