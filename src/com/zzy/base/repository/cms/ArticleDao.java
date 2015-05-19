package com.zzy.base.repository.cms;

import com.zzy.base.entity.cms.Article;
import com.zzy.base.entity.cms.CategoryArticle;
import com.zzy.base.repository.MyBatisRepository;

@MyBatisRepository
public interface ArticleDao {

	public abstract void add(Article article); 
	public abstract void addCategoryRelate(CategoryArticle ac); 
	public abstract void deleteById(Long[] array);
	public abstract int isRealArticleId(Integer id);
	public abstract Article findById(Integer id);
	public abstract Integer getRelateCategoryId(Integer id);
}
