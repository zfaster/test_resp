package mybatis.spring;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.zzy.base.entity.chat.ChatLog;
import com.zzy.base.entity.chat.Message;
import com.zzy.base.entity.chat.User;
import com.zzy.base.entity.cms.Article;
import com.zzy.base.entity.cms.Attachment;
import com.zzy.base.entity.cms.Category;
import com.zzy.base.entity.cms.CategoryArticle;
import com.zzy.base.service.HelloService;
import com.zzy.base.service.chat.ChatLogService;
import com.zzy.base.service.chat.DwrScriptSessionService;
import com.zzy.base.service.chat.MessageService;
import com.zzy.base.service.chat.UserService;
import com.zzy.base.service.cms.ArticleService;
import com.zzy.base.service.cms.AttachmentService;
import com.zzy.base.service.cms.CategoryService;
import com.zzy.base.service.spider.SpiderService;

public class CmsTest {
	static ApplicationContext context = null;
	CategoryService categoryService;
	ArticleService articleService;
	SpiderService spiderService;
	AttachmentService attachmentService;
	MessageService messageService;
	UserService userService;
	DwrScriptSessionService dwrScriptSessionService;
	protected RedisTemplate<String,Object> redisTemplate;
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("application*.xml");
		categoryService = (CategoryService) context.getBean("categoryService");
		articleService = (ArticleService) context.getBean("articleService");
		spiderService = (SpiderService) context.getBean("spiderService");
		attachmentService = (AttachmentService) context.getBean("attachmentService");
		messageService = (MessageService)context.getBean("messageService");
		userService = (UserService)context.getBean("userService");
		redisTemplate = (RedisTemplate)context.getBean("redisTemplate");
		dwrScriptSessionService = (DwrScriptSessionService)context.getBean("dwrScriptSessionService");
	}
	@Test
	public void addAttach(){
		Attachment attach = new Attachment();
		attach.setArticleId(1);
		attach.setContentType("doc");
		attach.setTitle("has");
		attach.setUploadTime(new Date());
		attachmentService.add(attach);
		System.out.println(attach.getAttachId());
	}
	@Test
	public void addCategory(){
		Article a  = articleService.getRandomArticle();
		System.out.println(a);
		//categoryService.add(new Category(null,"文艺频道","无描述",null));
		//categoryService.addIfNotExists("科技频道", "我是一级");
	}
	@Test
	public void findCategory(){
		Category parent = categoryService.findByName("科技频道");
		categoryService.add(new Category(null,"生活频道","无描述",parent.getCategoryId()));
	}
	@Test
	public void addArticle(){
		articleService.add(new Article("测试文章", "测试测试", "历史","未知", "haha ", new Date()));
	}
	@Test
	public void addArticleRelate(){
		articleService.addCategoryRelate(new CategoryArticle(1,1));
	}
	@Test
	public void addMsg(){
		messageService.add(new Message(null, 1, 2, "哈哈", new Date(), (short) 0));
	}
	@Test
	public void addUser(){
		for(int i=1;i<=10;i++){
			userService.add(new User("test"+i, "test"));
		}
		//messageService.add(new Message(null, 1, 2, "哈哈", new Date(), (short) 0));
	}
	@Test
	public void login(){
		User u = userService.login("test1", "test",(short)1);
		System.out.println(u);
	}

	@Test
	public void findMsg() {
		List<Message> list= messageService.getMessageByCondition(13, null, null);
		System.out.println(list.get(0).getSenderName());
	}
	@Test
	public void updateMsg() {
		ChatLogService dao = (ChatLogService) context.getBean("chatLogService");
		//ChatLog log = new ChatLog(1, 2, new Date(), new Date());
		dao.getNoFinishedUserChat(1);
		//messageService.changeMsgFlag(-1, 13);
	}
	@Test
	public void add() {
		Long tmp = new Long("1");
		Map<String,Long> map = new HashMap<>();
		map.put("a", tmp);
		Long b = map.get("a")+1;
		System.out.println(map.get("a"));
	}
	@Test
	public void deleteMsg() {
		ChatLogService dao = (ChatLogService) context.getBean("chatLogService");
		ChatLog cl = new ChatLog();
		cl.setLogId("dd58937920d845e5a872f68634ffbc71");
		dao.delete(cl);
		//messageService.changeMsgFlag(-1, 13);
	}
	@Test
	public void rmi() throws Exception {
		RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean(); 
		rmiProxyFactoryBean.setServiceInterface(HelloService.class); 
		rmiProxyFactoryBean.setServiceUrl("rmi://192.168.56.2:1099/HelloService");
		rmiProxyFactoryBean.afterPropertiesSet();
		HelloService hs =  (HelloService) rmiProxyFactoryBean.getObject();
		
		//HelloService hs =  (HelloService) Naming.lookup("rmi://127.0.0.1:1099/HelloService"); 
		
		//HelloService hs =  (HelloService) context.getBean("serviceClient");
		hs.sayHello();
	}
	@Test
	public void redis() {
		boolean success =  redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				
				 RedisSerializer<String> serializer = redisTemplate.getStringSerializer(); 
	                    byte[] key  = serializer.serialize("user123456");  
	                    byte[] name = serializer.serialize("zhangsan");  
	                    connection.setNX(key, name);  
	                return true;  
			}
			
		});
		System.out.println("a");
		Long l = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
					return connection.sAdd(serializer.serialize("userset"), serializer.serialize("123"));
			}
		});
		System.out.println(l);
		l = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
				return connection.sRem(serializer.serialize("userset"), serializer.serialize("123"));
			}
		});
		System.out.println(l);
		l = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = redisTemplate
						.getStringSerializer();
				return connection.sRem(serializer.serialize("userset"), serializer.serialize("123"));
			}
		});
		System.out.println(l);
		System.out.println(success);
		
	}
	@Test
	public void userCacheInfo(){
		Integer userId = 123456;
		dwrScriptSessionService.saveUserCacheInfo(userId, "abcdefg");
		dwrScriptSessionService.clearUserCacheInfo(userId);
		System.out.println(dwrScriptSessionService.getUserIp(userId));
		System.out.println(dwrScriptSessionService.getUserScriptSessionId(userId));
	}
	
	@Test
	public void saveMsg(){
		ValueOperations<String,Object> ops =  redisTemplate.opsForValue();
		Message msg = new Message(1,2,"asd");
		msg.setMsgid(UUID.randomUUID().toString());
		redisTemplate.setValueSerializer(new JacksonJsonRedisSerializer<>(Message.class));  
		//RedisSerializer<Message> ser = new JacksonJsonRedisSerializer<>(Message.class);
		ops.set("msg", msg);
		Message mm = (Message) ops.get("msg");
		System.out.println(mm);
		System.out.println(mm.getContent());
	}
	@Test
	public void sendMsg(){
		final Message msg = new Message(1,2,"asd");
		redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection conn)
					throws DataAccessException {
				RedisSerializer<String> keyser = redisTemplate.getStringSerializer();
				RedisSerializer<Message> valueser = new JacksonJsonRedisSerializer<>(Message.class);
				return conn.lPush(keyser.serialize("user:"+msg.getReciverId()+":msgQueue"), valueser.serialize(msg));
			}
		});
		redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection conn)
					throws DataAccessException {
				RedisSerializer<String> keyser = redisTemplate.getStringSerializer();
				List<byte[]> list = conn.bRPop(60, keyser.serialize("user:"+msg.getReciverId()+":msgQueue"));
				RedisSerializer<Message> ser = new JacksonJsonRedisSerializer<>(Message.class);
					System.out.println(keyser.deserialize(list.get(1)));
					 System.out.println(ser.deserialize(list.get(1)));
				return null;
			}
		});
	}
}
