package com.zzy.base.service.chat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.zzy.base.entity.chat.ChatLog;
import com.zzy.base.entity.chat.Message;
import com.zzy.base.entity.chat.User;
import com.zzy.base.repository.chat.MessageDao;
import com.zzy.base.utils.UnicodeConverter;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Resource
	MessageDao messageDao;
	@Resource
	RedisTemplate<String, Object> redisTemplate;
	@Resource
	ChatLogService chatLogService;
	@Override
	public void add(Message message) {
		messageDao.add(message);
	}
	@Override
	public List<Message> getMessageByCondition(Integer senderId,
			Integer reciverId, Short flag) {
		return messageDao.getMessageByCondition(senderId, reciverId, flag);
	}
	@Override
	public void changeMsgFlag(Integer senderId, Integer reciverId) {
		messageDao.changeMsgFlag(senderId, reciverId);
	}
	@Override
	public String getReciveMessage(final Serializable userId) {
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection conn)
					throws DataAccessException {
				RedisSerializer<String> keyser = redisTemplate.getStringSerializer();
				List<byte[]> list =  conn.bRPop(60, keyser.serialize("user:"+userId+":msgQueue"));
				if(list!=null && list.size()>0){
					System.out.println(keyser.deserialize(list.get(1)));
					return keyser.deserialize(list.get(1));
				}
				return null;
			}
		});
	}
	@Override
	public Long sendMessage(final Message message) {
			if(message.getType()!=Message.TAKE && message.getType()!=Message.BREAK && message.getType()!=Message.ENDSERVER){
				if(StringUtils.isNotEmpty(message.getContent())){
					message.setContent(UnicodeConverter.toEncodedUnicode(message.getContent(),false));
				}
			}
			if(StringUtils.isNotEmpty(message.getSenderName())){
				message.setSenderName(UnicodeConverter.toEncodedUnicode(message.getSenderName(),false));
			}
		Long result = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection conn)
					throws DataAccessException {
				RedisSerializer<String> keyser = redisTemplate.getStringSerializer();
				RedisSerializer<Message> valueser = new JacksonJsonRedisSerializer<>(Message.class);
				return conn.lPush(keyser.serialize("user:"+message.getReciverId()+":msgQueue"), valueser.serialize(message));
			}
		});
		
		if(message.getType()==1){//聊天消息保存
			if(result>0){
				message.setFlag((short)1);
			}else{
				message.setFlag((short)2);
			}
			this.add(message);
		}
		return result;
	}
	@Override
	public void buildUserServerRelation(User user, User server) {
		Message msg = new Message(user.getUserId(),server);
		ChatLog log = new ChatLog(server.getUserId(), user.getUserId(), new Date(), null);
		chatLogService.save(log);
		this.sendMessage(msg);
	}
	@Override
	public void destoryServerRelation(User server) {
		List<ChatLog> logs =chatLogService.getNoFinishedServerChat(server.getUserId());
		if(logs!=null){
			for(ChatLog log : logs){
				Message msg = new Message(log.getUserId(),server,Message.ENDSERVER);
				this.sendMessage(msg);
				log.setEndTime(new Date());
				chatLogService.updateNotNull(log);
			}
		}
	}

}
