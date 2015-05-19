package com.zzy.base.service.chat;

import java.io.Serializable;
import java.util.List;

import com.zzy.base.entity.chat.Message;
import com.zzy.base.entity.chat.User;

public interface MessageService {
	public void add(Message message);
	public List<Message> getMessageByCondition(Integer senderId,Integer reciverId,Short flag);
	public void changeMsgFlag(Integer senderId, Integer reciverId);
	/**
	 * 通过redis阻塞队列获得消息
	 * @param userId
	 * @return
	 */
	public String getReciveMessage(Serializable userId);
	public Long sendMessage(Message message);
	/**
	 * 建立连接并通知客户端
	 * @param user
	 * @param server
	 */
	public void buildUserServerRelation(User user,User server);
	/**
	 * 销毁连接并通知客户端
	 * @param user
	 * @param server
	 */
	public void destoryServerRelation(User server);
}
