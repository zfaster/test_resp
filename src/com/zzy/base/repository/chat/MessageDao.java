package com.zzy.base.repository.chat;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zzy.base.entity.chat.Message;
import com.zzy.base.entity.chat.User;
import com.zzy.base.repository.MyBatisRepository;

@MyBatisRepository
public interface MessageDao {
	public abstract void add(Message message); 
	public abstract List<Message> getMessageByCondition(@Param("senderId")Integer senderId,
			@Param("reciverId")Integer reciverId,
			@Param("flag")Short flag);
	public abstract void changeMsgFlag(@Param("senderId")Integer senderId, @Param("reciverId")Integer reciverId);
}
