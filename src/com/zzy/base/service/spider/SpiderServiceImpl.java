package com.zzy.base.service.spider;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzy.base.entity.cms.Article;
import com.zzy.base.entity.cms.Category;
import com.zzy.base.entity.cms.CategoryArticle;
import com.zzy.base.repository.cms.ArticleDao;
import com.zzy.base.utils.spider.Spider;
@Service("spiderService")
public class SpiderServiceImpl implements SpiderService {

	@Resource
	private ArticleDao articleDao;

	public List<Article> collect(String url) {
		Spider spider = Spider.getInstance(url);
		List<Article> articles = spider.collect(url);
		if(articles != null){
			for(Article a:articles){
				//a.setCreateTime(new Date());
				a.setType("copy");
				Integer cId = a.getCategoryId();
				
				try {
					articleDao.add(a);
					if(cId!=null){
						articleDao.addCategoryRelate(new CategoryArticle(a.getArticleId(), cId));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return articles;
	}

}
