package com.swpu.sharebook.entity;

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
public class Remak implements Serializable {

    private static final long serialVersionUID=1L;

    private Integer id;

    /**
     * 外键
     */
    @TableField("orderId")
    private Integer orderId;

    /**
     * 外键 
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 外键
     */
    @TableField("bId")
    private Integer bId;

    /**
     * 外键配送员id
     */
    @TableField("deliveryId")
    private Integer deliveryId;

    /**
     * 外键 管理员id
     */
    @TableField("administrtorId")
    private Integer administrtorId;

    @TableField("userToOrder")
    private String userToOrder;

    @TableField("userToDelivery")
    private String userToDelivery;

    @TableField("administrtorToUser")
    private String administrtorToUser;
}
