package com.zzy.base.common.manager.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.zzy.base.common.manager.OnlineUserManager;
import com.zzy.base.entity.chat.ChatLog;
import com.zzy.base.entity.chat.Message;
import com.zzy.base.entity.chat.User;
import com.zzy.base.service.chat.ChatLogService;
import com.zzy.base.service.chat.MessageService;
import com.zzy.base.service.chat.UserService;

public class OnlineUserManagerClusterImpl implements OnlineUserManager {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	private static final String ONLINE_USER_SET = "onlineSetUser";
	private static final String ONLINE_SERVER_SET = "onlineSetServer";
	private static final String WAITTING_USER_QUEUE = "waittingUser";
	private static final String WAITTING_USER_SET = "waittingUserSet";
	@Resource
	private UserService userService;
	@Resource
	private MessageService messageService;
	@Resource
	private ChatLogService chatLogService;
	@Override
	public void online(final User user) {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
				if (user.getType() == 1) {
					connection.sAdd(serializer.serialize(ONLINE_USER_SET),
							serializer.serialize(user.getUserId().toString()));
					Long result = connection.sAdd(serializer.serialize(WAITTING_USER_SET),
							serializer.serialize(user.getUserId().toString()));
					messageService.sendMessage(new Message(user.getUserId(),""));//发送空消息防止刷新页面后上一页面阻塞队列接受到正常消息
					if(result>0){//进入等待队列说明是第一次进入
						connection.rPush(serializer.serialize(WAITTING_USER_QUEUE),
								serializer.serialize(user.getUserId().toString()));
					}
					List<ChatLog> list = chatLogService.getNoFinishedUserChat(user.getUserId());
					if(list!=null){
						for(ChatLog log : list){//获得与user建立连接的server
							log.setEndTime(new Date());
							chatLogService.updateNotNull(log);
							messageService.sendMessage(new Message(log.getServerId(),user,Message.BREAK));
						}
					}
				} else {
					connection.sAdd(serializer.serialize(ONLINE_SERVER_SET),
							serializer.serialize(user.getUserId().toString()));
				}
				return true;
			}
		});

	}

	@Override
	public void offline(final User user) {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
				if (user.getType() == 1) {
					connection.sRem(serializer.serialize(ONLINE_USER_SET),
							serializer.serialize(user.getUserId().toString()));
					Long result = connection.sRem(serializer.serialize(WAITTING_USER_SET),
							serializer.serialize(user.getUserId().toString()));
					if(result>0){
						connection.lRem(serializer.serialize(WAITTING_USER_QUEUE), 1, serializer.serialize(user.getUserId().toString()));
					}
					
				} else {
					connection.sRem(serializer.serialize(ONLINE_SERVER_SET),
							serializer.serialize(user.getUserId().toString()));
				}
				return true;
			}
		});

	}

	@Override
	public boolean isOnline(final Serializable userId) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
				boolean exsist = false;
				
				exsist = connection.sIsMember(serializer.serialize(ONLINE_USER_SET),
							serializer.serialize(userId.toString()));
				if(!exsist){
					exsist = connection.sIsMember(serializer.serialize(ONLINE_SERVER_SET),
							serializer.serialize(userId.toString()));
				}
				return exsist;
			}
		});
	}

	@Override
	public User getOnlineUser(final Serializable userId) {
		String id = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
				boolean exists = connection.sIsMember(serializer.serialize(ONLINE_USER_SET), serializer.serialize(userId.toString()));
				if(!exists ){
					return null;
				}else{
					return userId.toString();
				}
			}
		});
		if(id!=null){
			return userService.findById(Integer.parseInt(id));
		}
		
		return null;
	}

	@Override
	public User getOnlineService(final Serializable userId) {
		String id = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
					boolean exists = connection.sIsMember(serializer.serialize(ONLINE_SERVER_SET), serializer.serialize(userId.toString()));
				if(!exists ){
					return null;
				}else{
					return userId.toString();
				}
			}
		});
		if(id!=null){
			return userService.findById(Integer.parseInt(id));
		}
		
		return null;
	}

	@Override
	public Collection<User> getWaitingUser() {
		Collection<Integer> result = redisTemplate.execute(new RedisCallback<Collection<Integer>>() {
			@Override
			public Collection<Integer> doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
				Collection<byte[]> sets = connection.lRange(serializer.serialize(ONLINE_USER_SET), 0, -1);
				Collection<Integer> result = new HashSet<>();	
				for(byte[] b: sets){
					result.add(Integer.parseInt(serializer.deserialize(b)));
				}
				return result;
			}
		});
		List<User> users = new ArrayList<>();
		for(Integer id : result){
			users.add(userService.findById(id));
		}
		return users;
	}

	@Override
	public User popWaitingUser() {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				byte[] result = connection.lPop(serializer.serialize(WAITTING_USER_QUEUE));
				if(result!=null){
					connection.sRem(serializer.serialize(WAITTING_USER_SET),result);
					return serializer.deserialize(result);
				}
				return null;
			}
		});
		if(result!=null){
			return userService.findById(Integer.parseInt(result));
		}else{
			return null;
		}
	}

	@Override
	public Long getOnlineServerNum() {
		return redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
				return connection.sCard(serializer.serialize(ONLINE_SERVER_SET));
			}
		});
	}

}
