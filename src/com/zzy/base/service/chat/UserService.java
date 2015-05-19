package com.zzy.base.service.chat;

import java.util.List;

import com.zzy.base.entity.chat.User;

public interface UserService {
	public void add(User user);
	public User findById(Integer id);
	public List<User> findList();
	public User login(String username,String password,Short type);
	public User getOnlineServiceByRandom();
}
