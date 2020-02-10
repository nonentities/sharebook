package com.swpu.sharebook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2020-01-26
 */
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "r_id", type = IdType.AUTO)
    private Integer rId;

    @TableField("roleName")
    private String roleName;


}
