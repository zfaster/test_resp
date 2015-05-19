package com.zzy.base.repository.chat;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zzy.base.entity.chat.User;
import com.zzy.base.repository.MyBatisRepository;

@MyBatisRepository
public interface UserDao {
	public abstract void add(User user); 
	public abstract User findById(Integer id); 
	public abstract List<User> findList(); 
	public abstract User login(@Param("username")String username,@Param("password") String password,@Param("type") Short type); 
	public abstract List<User> findUserByType(Short s);
}
