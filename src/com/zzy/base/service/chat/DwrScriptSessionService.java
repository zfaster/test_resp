package com.zzy.base.service.chat;

import java.io.Serializable;

public interface DwrScriptSessionService {
	void saveUserCacheInfo(Serializable userId,String scriptSessionId);
	String getUserIp(Serializable userId);
	String getUserScriptSessionId(Serializable userId);
	void clearUserCacheInfo(Serializable userId);
}
