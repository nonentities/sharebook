package com.swpu.sharebook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("booksource")
public class BookSource implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 书籍来源时间
     */
    @TableField("sourceTime")
    private LocalDateTime sourceTime;

    /**
     * 赠书用户id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 赠送书籍id
     */
  private Book book;

    /**
     * 书籍是否被审核，默认为零，审核后自动将审核人的id插入
     */
    @TableField("boolPass")
    private Integer boolPass;

    /**
     * 捐赠 书籍的数量
     */
    @TableField("bookAccount")
    private Integer bookAccount;


}
