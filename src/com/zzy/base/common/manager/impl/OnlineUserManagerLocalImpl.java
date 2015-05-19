package com.zzy.base.common.manager.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.zzy.base.common.manager.OnlineUserManager;
import com.zzy.base.entity.chat.User;
public class OnlineUserManagerLocalImpl implements OnlineUserManager {

	private static Map<Integer, User> loginUser = new ConcurrentHashMap<Integer, User>();
	private static Map<Integer, User> loginService = new ConcurrentHashMap<Integer, User>();
	private static  Map<Integer, User> waitQueue = new ConcurrentHashMap<Integer, User>();
	@Override
	public void online(User user){
		if(user.getType()==1){
			loginUser.put(user.getUserId(), user);
			waitQueue.put(user.getUserId(), user);
		}else{
			loginService.put(user.getUserId(), user);
		}
	}
	@Override
	public void offline(User user){
		if(user.getType()==1){
			loginUser.remove(user.getUserId());
		}else{
			loginService.remove(user.getUserId());
		}
	}
	@Override
	public boolean isOnline(Serializable userId){
		return loginUser.containsKey(userId) || loginService.containsKey(userId);
	}
	@Override
	public User getOnlineUser(Serializable userId){
		return loginUser.get(userId);
	}
	@Override
	public User getOnlineService(Serializable userId){
		return loginService.get(userId);
	}
	@Override
	public Collection<User> getWaitingUser(){
		return waitQueue.values();
	}
	@Override
	public User popWaitingUser() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long getOnlineServerNum() {
		// TODO Auto-generated method stub
		return null;
	}
}
