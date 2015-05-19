package com.zzy.base.entity.chat;

import java.io.Serializable;

import org.directwebremoting.annotations.DataTransferObject;

import com.fasterxml.jackson.annotation.JsonIgnore;

@DataTransferObject
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String username;
	@JsonIgnore
	private String password;
	/**
	 * 1:普通用户 2:客服
	 */
	private Short type;
	
	public User() {
		super();
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.type = 1;
	}
	
	public User(String username, String password, Short type) {
		super();
		this.username = username;
		this.password = password;
		this.type = type;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	
}
