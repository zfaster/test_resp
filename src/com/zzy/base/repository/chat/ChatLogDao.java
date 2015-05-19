package com.zzy.base.repository.chat;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zzy.base.entity.chat.ChatLog;
import com.zzy.base.repository.MyBatisRepository;
import com.zzy.mybatis.mapper.Mapper;

@MyBatisRepository
public interface ChatLogDao extends Mapper<ChatLog> {
	
	public List<ChatLog> getNoFinishedChat(@Param("userId") Integer userId,@Param("serverId") Integer serverId);
	
}
