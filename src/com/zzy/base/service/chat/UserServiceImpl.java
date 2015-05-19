package com.zzy.base.service.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzy.base.common.manager.OnlineUserManager;
import com.zzy.base.entity.chat.User;
import com.zzy.base.repository.chat.UserDao;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	UserDao userDao;
	@Resource
	OnlineUserManager onlineUserManager;
	@Override
	public void add(User user) {
		userDao.add(user);
	}
	@Override
	public User findById(Integer id) {
		return userDao.findById(id);
	}
	@Override
	public List<User> findList() {
		return userDao.findList();
	}
	@Override
	public User login(String username, String password,Short type) {
		return userDao.login(username, password,type);
	}
	@Override
	public User getOnlineServiceByRandom() {
		List <User> list = userDao.findUserByType((short)2);
		List<User> online = new ArrayList<>();
		
		if(list!=null && list.size()>0){
			for(User u : list){
				if(onlineUserManager.isOnline(u.getUserId())){
					online.add(u);
				}
			}
		}
		if(online.size()>0){
			return online.get(new Random().nextInt(online.size()));
		}
		return null;
	}
}
