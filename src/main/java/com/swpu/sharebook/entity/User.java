package com.swpu.sharebook.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("user")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@TableId(value = "id" ,type = IdType.AUTO)
	private Integer id;
	@TableField("userName")
	@NotBlank(message = "用户名不能为空或者空格")
	private String userName;
	@TableField("studentId")
	//学号的正则表达式先不着急写
	@NotBlank(message = "学号不能为空或者空串")
	@Pattern(regexp = "^[2][0](([1][7-9])|([2][0-2]))[3][1][7][6-8][1-4][1-2][0-6][0-9]",message = "学号格式不合法")
	private String studentId;
	@TableField("wechatNumber")
	@NotBlank(message = "手机号不能为空或者空串")
	@Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[3-8]{1})|(18[0-9]{1})|(19[0-9]{1})|(14[5-7]{1}))+\\d{8})$",message ="手机号格式不正确")
	private String wechatNumber;
	@NotBlank(message = "密码不能为空或者空串")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[\\s\\S]{8,16}$",message = "至少8-16个字符，至少1个大写字母，1个小写字母和1个数字，其他可以是任意字符")
	private String password;
	@TableField("dirmitoryNumber")
	@NotBlank(message = "宿舍不能为空")
	private String dirmitoryNumber;
	@Min(value = 0)
	private Integer integration;
	@TableField(exist = false)
	private List<Role> role;

	@TableField("salt")
	private String salt;
	@TableField("headPortrait")
	@Pattern(regexp = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]",message = "头像位置不合法")
	private String headPortrait;
	@TableField("deliveManGrade")
	private Integer deliveManGrade;

}

