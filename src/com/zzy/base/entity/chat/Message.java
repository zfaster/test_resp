package com.zzy.base.entity.chat;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.directwebremoting.annotations.DataTransferObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zzy.base.utils.JacksonMapper;
@DataTransferObject
public class Message implements Serializable{

	/**
	 * 聊天
	 */
	public static final short CHATTING = 1;
	/**
	 * 接入通知
	 */
	public static final short TAKE = 2;
	/**
	 * 系统通知
	 */
	public static final short SYSTEM = 3;
	/**
	 * 断开通知
	 */
	public static final short BREAK = 4;
	
	/**
	 * 结束服务
	 */
	public static final short ENDSERVER = 5;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonIgnore
	private String msgid;
	private Integer reciverId;
	private Integer senderId;
	private String content;
	private Date sendTime = new Date();
	/**
	 * 接受者是否已经接收到了消息
	 */
	@JsonIgnore
	private Short flag;
	/**
	 * 消息类型 1：正常消息 2：系统消息
	 */
	private Short type;
	private String senderName;
	public Message() {
		super();
	}
	
	public Message(Integer reciverId, User user) {
		super();
		this.senderId = 0;
		this.setSenderName(user.getUsername());
		this.reciverId = reciverId;
		try {
			this.content = JacksonMapper.getInstance().writeValueAsString(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		this.type = Message.TAKE;
	}
	public Message(Integer reciverId, User user,Short type) {
		super();
		this.senderId = 0;
		this.setSenderName(user.getUsername());
		this.reciverId = reciverId;
		try {
			this.content = JacksonMapper.getInstance().writeValueAsString(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		this.type = type;
	}
	public Message(Integer reciverId, String content) {
		super();
		this.senderId = 0;
		this.setSenderName("系统消息");
		this.reciverId = reciverId;
		this.content = content;
		this.type = Message.SYSTEM;
	}
	public Message(Integer senderId,Integer reciverId, String content) {
		super();
		this.senderId = senderId;
		this.content = content;
		this.reciverId = reciverId;
		this.sendTime = new Date();
		this.type = Message.CHATTING;
	}
	
	public Message(String msgid, Integer reciverId, Integer senderId,
			String content, Date sendTime, Short flag) {
		super();
		this.msgid = msgid;
		this.reciverId = reciverId;
		this.senderId = senderId;
		this.content = content;
		this.sendTime = sendTime;
		this.flag = flag;
	}
	public String getMsgid() {
		if(msgid==null){
			msgid = UUID.randomUUID().toString().replaceAll("-", "");
		}
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public Integer getReciverId() {
		return reciverId;
	}
	public void setReciverId(Integer reciverId) {
		this.reciverId = reciverId;
	}
	public Integer getSenderId() {
		return senderId;
	}
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Short getFlag() {
		return flag;
	}
	public void setFlag(Short flag) {
		this.flag = flag;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
	
}
