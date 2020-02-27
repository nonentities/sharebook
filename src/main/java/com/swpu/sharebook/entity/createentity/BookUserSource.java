package com.swpu.sharebook.entity.createentity;
import com.swpu.sharebook.entity.Book;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class BookUserSource implements Serializable {
    private static final long serialVersionUID=1L;

    private Integer id;

    /**
     * 书籍来源时间
     */
    private LocalDateTime sourceTime;

    /**
     * 赠书用户id
     */
    private Integer userId;
    /**
     * 用户的名字
     */
    private String userName;
    /**
     * 微信号
     */
    private String wechatNumber;

    /**
     * 用户学号
     */
    private String studentId;
    private String dirmitoryNumber;
    /**
     * 用户头像
     */
    private String headPortrait;
    /**
     * 赠送书籍id
     */
    private Book book;

    /**
     * 书籍是否被审核，默认为零，审核后自动将审核人的id插入
     */
    private Integer boolPass;

    /**
     * 捐赠 书籍的数量
     */
    private Integer bookAccount;

}
