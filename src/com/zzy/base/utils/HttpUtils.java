package com.zzy.base.utils;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Parser;

public class HttpUtils {

	public static String getHtml(HttpClient httpclient,String url){
		HttpResponse response = null;
		try {
			HttpGet get = new HttpGet(url);
			response = httpclient.execute(get);
			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			byte[] content = IOUtils.toByteArray(is);
			if(entity != null){
				Charset chr =  ContentType.getOrDefault(entity).getCharset();
				String charset = null;
				if(chr!=null){
					charset = chr.displayName();
				}
				
				String html = null;
				if(charset == null){
					html = new String(content,"iso-8859-1");
					
					Parser parser = new Parser();
					parser.setInputHTML(html);
					parser.parse(null);
					charset = parser.getEncoding();
				}
				if(charset == null){
					charset = "iso-8859-1";
				}
				is.close();
				return new String(content,charset);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] getImage(HttpClient httpclient,String url){
		try {
			HttpGet get = new HttpGet(url);
			HttpResponse response = httpclient.execute(get);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				InputStream is = entity.getContent();
				return IOUtils.toByteArray(is);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
