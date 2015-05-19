package com.zzy.base.common.manager;

import java.io.Serializable;
import java.util.Collection;

import com.zzy.base.entity.chat.User;

public interface OnlineUserManager {

	public abstract void online(User user);

	public abstract void offline(User user);

	public abstract boolean isOnline(Serializable userId);

	public abstract User getOnlineUser(Serializable userId);

	public abstract User getOnlineService(Serializable userId);

	public abstract Collection<User> getWaitingUser();
	/**
	 * 从队列取出第一个元素
	 * @return
	 */
	public abstract User popWaitingUser();
	
	public abstract Long getOnlineServerNum();
}