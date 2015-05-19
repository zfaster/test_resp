package com.zzy.base.utils.spider;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.zzy.base.entity.cms.Article;
import com.zzy.base.utils.spider.impl.SpiderForjj59;

public abstract class Spider {

	protected HttpClient httpClient;
	protected String url;
	protected HttpContext context;
	
	private static Map<String,Class<? extends Spider>> spiders = new HashMap<String,Class<? extends Spider>>();
	static{
		spiders.put("www.jj59.com", SpiderForjj59.class);
	}
	
	
	public static Spider getInstance(String url){
		try {
			URL u = new URL(url);
			return spiders.get(u.getHost()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("无对应【"+url+"】的爬虫程序");
		}
	}
	
	public List<Article> collect(String url){
		this.httpClient = HttpClientBuilder.create().build();  
		this.context = new BasicHttpContext();
		this.url = url;
		
		excute();
		
		List<Article> articles =(List<Article>)context.getAttribute("articles");
		//httpClient.getConnectionManager().shutdown();
		return articles;
		
	}
	
	public abstract void excute();
}
