package com.zzy.base.entity.chat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="t_chatlog")
public class ChatLog {
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name="logid")
	private String logId;
	@Column(name="serverid")
	private Integer serverId;
	@Column(name="userid")
	private Integer userId;
	@Column(name="beginTime")
	private Date beginTime;
	@Column(name="endTime")
	private Date endTime;

	

	public ChatLog(Integer serverId, Integer userId, Date beginTime,
			Date endTime) {
		super();
		this.serverId = serverId;
		this.userId = userId;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	public ChatLog() {
		super();
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}


	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
