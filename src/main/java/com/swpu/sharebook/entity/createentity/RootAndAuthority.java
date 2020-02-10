package com.swpu.sharebook.entity.createentity;

import java.io.Serializable;
import java.util.Date;

import com.swpu.sharebook.entity.Authority;

public class RootAndAuthority implements Serializable {
private Authority authority;
private Date createTime;
private Date cancelTime;
public Authority getAuthority() {
	return authority;
}
public void setAuthority(Authority authority) {
	this.authority = authority;
}
public Date getCreateTime() {
	return createTime;
}
public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}
public Date getCancelTime() {
	return cancelTime;
}
public void setCancelTime(Date cancelTime) {
	this.cancelTime = cancelTime;
}
@Override
public String toString() {
	return "RootAndAuthority [authority=" + authority + ", createTime=" + createTime + ", cancelTime=" + cancelTime
			+ "]";
}
public RootAndAuthority() {
	super();
	// TODO Auto-generated constructor stub
}
}
