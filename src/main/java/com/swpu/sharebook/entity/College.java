package com.swpu.sharebook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class College implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学院名
     */
    @TableField("collegeName")
    private String collegeName;


}
