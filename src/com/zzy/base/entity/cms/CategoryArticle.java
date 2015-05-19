package com.zzy.base.entity.cms;

public class CategoryArticle {

	private Integer articleId;
	private Integer categoryId;

	
	public CategoryArticle() {
		super();
	}

	public CategoryArticle(Integer articleId, Integer categoryId) {
		super();
		this.articleId = articleId;
		this.categoryId = categoryId;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

}
