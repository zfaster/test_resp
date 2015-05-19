package com.zzy.base.entity.cms;

import java.util.Date;
import java.util.Set;

public class Article {
	private Integer articleId;
	private String title;// 标题
	private String content;// 内容
	private String source;// 来源
	private String author; // 作者
	private String keyword; // 关键字
	private String intro; // 简介
	private String type; // 分类
	private boolean recommend; // 是否推荐阅读
	private boolean headline; // 是否作为首页头条
	private int leaveNumber; // 留言数
	private int clickNumber; // 点击量
	private Set<Category> categorys; // 所属频道
	private Date createTime; // 创建时间
	private Date updateTime; // 更新时间
	private Date deployTime; // 发布时间

	private Integer categoryId;
	

	public Article(String title, String content, String author, String keyword,
			String type, Date createTime) {
		super();
		this.title = title;
		this.content = content;
		this.author = author;
		this.keyword = keyword;
		this.type = type;
		this.createTime = createTime;
	}

	public Article(Integer articleId, String title, String content,
			String source, String author, String keyword, String intro,
			String type, boolean recommend, boolean headline, int leaveNumber,
			int clickNumber, Set<Category> categorys, Date createTime,
			Date updateTime, Date deployTime) {
		super();
		this.articleId = articleId;
		this.title = title;
		this.content = content;
		this.source = source;
		this.author = author;
		this.keyword = keyword;
		this.intro = intro;
		this.type = type;
		this.recommend = recommend;
		this.headline = headline;
		this.leaveNumber = leaveNumber;
		this.clickNumber = clickNumber;
		this.categorys = categorys;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.deployTime = deployTime;
	}

	public Article() {
		super();
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public boolean isHeadline() {
		return headline;
	}

	public void setHeadline(boolean headline) {
		this.headline = headline;
	}

	public int getLeaveNumber() {
		return leaveNumber;
	}

	public void setLeaveNumber(int leaveNumber) {
		this.leaveNumber = leaveNumber;
	}

	public int getClickNumber() {
		return clickNumber;
	}

	public void setClickNumber(int clickNumber) {
		this.clickNumber = clickNumber;
	}

	public Set<Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(Set<Category> categorys) {
		this.categorys = categorys;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getDeployTime() {
		return deployTime;
	}

	public void setDeployTime(Date deployTime) {
		this.deployTime = deployTime;
	}


	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "Article [articleId=" + articleId + ", title=" + title
				+ ", content=" + content + ", source=" + source + ", author="
				+ author + ", keyword=" + keyword + ", intro=" + intro
				+ ", type=" + type + ", recommend=" + recommend + ", headline="
				+ headline + ", leaveNumber=" + leaveNumber + ", clickNumber="
				+ clickNumber + ", categorys=" + categorys + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", deployTime="
				+ deployTime + "]";
	}

}