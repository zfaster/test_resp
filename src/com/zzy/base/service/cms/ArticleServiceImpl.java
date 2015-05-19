package com.zzy.base.service.cms;

import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzy.base.entity.cms.Article;
import com.zzy.base.entity.cms.CategoryArticle;
import com.zzy.base.repository.cms.ArticleDao;
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

	@Resource
	ArticleDao articleDao;
	@Override
	public void add(Article article) {
		articleDao.add(article);
	}
	@Override
	public void addCategoryRelate(CategoryArticle ac) {
		articleDao.addCategoryRelate(ac);
	}

	@Override
	public boolean isRealArticleId(Integer id) {
		return articleDao.isRealArticleId(id) == 1;
	}
	@Override
	public int getRandomArticleId(){
		int id = -1;
		do{
			id = new Random().nextInt(21111);
		}while(!this.isRealArticleId(id));
		return id;
	}
	@Override
	public Article getRandomArticle() {
		Article a = null;
		do{
			a = articleDao.findById(new Random().nextInt(21111));
		}while(a == null);
		return a;
	}
	@Override
	public Integer getRelateCategory(Integer id){
		return articleDao.getRelateCategoryId(id);
	}
}
