package com.zzy.base.service.chat;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zzy.base.entity.chat.ChatLog;
import com.zzy.base.repository.chat.ChatLogDao;
import com.zzy.base.service.BaseServiceImpl;
@Service("chatLogService")
public class ChatLogServiceImpl extends BaseServiceImpl<ChatLog> implements ChatLogService {

	@Override
	public List<ChatLog> getNoFinishedUserChat(Integer userId) {
		return ((ChatLogDao)mapper).getNoFinishedChat(userId, null);
	}

	@Override
	public List<ChatLog> getNoFinishedServerChat(Integer serverId) {
		return ((ChatLogDao)mapper).getNoFinishedChat(null, serverId);
	}
}
