package com.swpu.sharebook.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Data
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date orderTime;
	private Boolean orderStatus;
	//@NotNull(message ="书籍不能为空" )
	private Book book;
//	@NotNull(message = "用户不能为空")
	private User user;
	private Integer distrbutionId;
	private Integer orderBool;
	@Min(value = 10,message = "书籍数量必须大于一")
	private Integer bookAccount;
	private Boolean isPay;
	private Date returnDate;
	private  Boolean sendStatus;
	private String orderFlag;
}
