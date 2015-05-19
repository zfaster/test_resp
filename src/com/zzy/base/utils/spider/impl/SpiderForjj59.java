package com.zzy.base.utils.spider.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.MetaTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.SimpleNodeIterator;

import com.zzy.base.common.factory.SpringBeanFactory;
import com.zzy.base.entity.cms.Article;
import com.zzy.base.service.cms.CategoryService;
import com.zzy.base.utils.HttpUtils;
import com.zzy.base.utils.ParseUtils;
import com.zzy.base.utils.spider.Spider;

public class SpiderForjj59 extends Spider {
private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public void excute() {
		String html = HttpUtils.getHtml(httpClient, url);
		
		if(html == null){
			throw new RuntimeException();
		}
		Article a = new Article();
		a.setSource(url);
		
		List<MetaTag> metakeytags = ParseUtils.parseTags(html, MetaTag.class, "name", "keywords");
		if(metakeytags != null){
			a.setTitle(metakeytags.get(0).getMetaContent());
			a.setKeyword(metakeytags.get(0).getMetaContent());
		}
		List<MetaTag> metadestags = ParseUtils.parseTags(html, MetaTag.class, "name", "description");
		if(metadestags != null){
			a.setIntro(metadestags.get(0).getMetaContent());
		}
		List<Div> authortags = ParseUtils.parseTags(html, Div.class, "class", "info");
		if(authortags != null){
			if(authortags.size()>0){
				String meta = authortags.get(0).getStringText();
				try {
					a.setCreateTime(sdf.parse(StringUtils.substringBetween(meta,"时间:</small>", "<small>来源").trim()));
				} catch (ParseException e) {
					e.printStackTrace();
					a.setCreateTime(new Date());
				}
				try {
					a.setAuthor(StringUtils.substringBetween(meta,"作者:</small>", "<small>阅读").trim());
				} catch (Exception e) {
					a.setAuthor("");
				}
			}
				
			
		}
		List<Div> contenttags = ParseUtils.parseTags(html, Div.class, "id", "zt");
		SimpleNodeIterator contents = contenttags.get(0).children();
		String content = null;
		while(contents.hasMoreNodes()){
			Node node = contents.nextNode();
			if(node instanceof ParagraphTag){
				ParagraphTag link = (ParagraphTag) node;
				content = link.getStringText();
				break;
			}
		}
		a.setContent(content);
		List<Div> categorys = ParseUtils.parseTags(html, Div.class, "class", "dqwz");
		SimpleNodeIterator nodes = categorys.get(0).children();
		CategoryService categoryService = (CategoryService) SpringBeanFactory.getBean("categoryService");
		List<String> cats = new ArrayList<>();
		while(nodes.hasMoreNodes()){
			Node node = nodes.nextNode();
			if(node instanceof LinkTag){
				LinkTag link = (LinkTag) node;
				String nodexText = link.getLinkText().trim();
				if(nodexText.equals("首页")){
					continue;
				}
				cats.add(nodexText);
			}
		}
		String pName = null;
		Integer catId = 0;
		for(String cat : cats){
			catId = categoryService.addIfNotExists(cat, pName).getCategoryId();
			pName = cat;
		}
		a.setCategoryId(catId);
		/*List<ImageTag> iamges =  ParseUtils.parseTags(content,ImageTag.class);
		for(ImageTag image : iamges){
			byte[] imageByte = HttpUtils.getImage(httpClient, image.getImageURL());
			String imageName = FilenameUtils.getName( image.getImageURL()).trim();
			if(!new File(Attachment.DEFAULTPATH+imageName).exists()){
				IOUtils.write(imageByte, FileUtils.openOutputStream(new File(Attachment.DEFAULTPATH+imageName)));
			}
			Attachment attachment = new Attachment();
			attachment.setContentType("image/jpeg");
			attachment.setName(imageName);
			attachment.setUploadTime(new Date());
			a.addAttachment(attachment);
		}*/
		List<Article> articles = new ArrayList<Article>();
		articles.add(a);
		context.setAttribute("articles", articles);
	}

}
