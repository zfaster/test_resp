package com.zzy.base.service.chat;

import java.util.List;

import com.zzy.base.entity.chat.ChatLog;
import com.zzy.base.service.BaseService;

public interface ChatLogService extends BaseService<ChatLog>{
	public List<ChatLog> getNoFinishedUserChat(Integer userId);
	public List<ChatLog> getNoFinishedServerChat(Integer userId);
}
