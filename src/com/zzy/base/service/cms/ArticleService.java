package com.zzy.base.service.cms;

import com.zzy.base.entity.cms.Article;
import com.zzy.base.entity.cms.CategoryArticle;

public interface ArticleService {
	public void add(Article article);

	void addCategoryRelate(CategoryArticle ac);

	boolean isRealArticleId(Integer id);

	int getRandomArticleId();
	Article getRandomArticle();

	Integer getRelateCategory(Integer id);
}
