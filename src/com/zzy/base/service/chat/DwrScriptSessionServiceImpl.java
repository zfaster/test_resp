package com.zzy.base.service.chat;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.zzy.base.common.context.SystemContext;
@Service("dwrScriptSessionService")
public class DwrScriptSessionServiceImpl implements DwrScriptSessionService{
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Override
	public void saveUserCacheInfo(final Serializable userId,final  String scriptSessionId) {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection conn)
					throws DataAccessException {
				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				conn.set(ser.serialize(getUserScriptKey(userId.toString())), ser.serialize(scriptSessionId));
				conn.set(ser.serialize(getUserIpKey(userId.toString())), ser.serialize(SystemContext.LOCALIP));
				return true;
			}
			
		});
	}

	@Override
	public String getUserIp(final Serializable userId) {
		return redisTemplate.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection conn)
					throws DataAccessException {
				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				byte[] result = conn.get(ser.serialize(getUserIpKey(userId.toString())));
				if(result!=null){
					return ser.deserialize(result);
				}
				return null;
			}
			
		});
	}

	@Override
	public String getUserScriptSessionId(final Serializable userId) {
		return redisTemplate.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection conn)
					throws DataAccessException {
				RedisSerializer<String> ser = redisTemplate.getStringSerializer();
				byte[] result = conn.get(ser.serialize(getUserScriptKey(userId.toString())));
				if(result!=null){
					return ser.deserialize(result);
				}
				return null;
			}
			
		});
	}

	private String getUserScriptKey(String userId) {
		return "user:" + userId.toString() + ":sessionId";
	}

	private String getUserIpKey(String userId) {
		return "user:" + userId.toString() + ":ip";
	}

	@Override
	public void clearUserCacheInfo(Serializable userId) {
		Set<String> set = new HashSet<>();
		set.add(getUserScriptKey(userId.toString()));
		set.add(getUserIpKey(userId.toString()));
		redisTemplate.delete(set);
		
	}
}
