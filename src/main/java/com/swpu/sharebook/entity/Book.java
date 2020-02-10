package com.swpu.sharebook.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@TableName("book")
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "bId",type = IdType.AUTO)
    private Integer bId;
    @NotBlank(message = "书籍名称不能为空或者为空格")
    private String bName;
    @NotBlank(message = "书籍出版社必须不能为空或者空格")
    private String bPbulish;
    @NotBlank(message = "必须包含一些书籍简介")
    private String introduction;
    @NotBlank(message = "书籍作者不能为空或者空格")
    private String writer;
   @Size(min = 2,max = 100)
    @NotBlank(message = "书籍其他重要的备注信息")
    @Pattern(regexp = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]",message = "必须是一个合法的url地址")
    private String bookOtherImportantPath;
    @Min(value = 1)
    @Max(value = 10)
    private Integer bookAccount;

}
