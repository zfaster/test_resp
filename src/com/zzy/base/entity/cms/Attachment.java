package com.zzy.base.entity.cms;

import java.util.Date;
import java.util.UUID;

public class Attachment {
	private String attachId;
	private Integer articleId;
	/**
	 * 文件名
	 */
	private String title;
	/**
	 * 文件类型
	 */
	private String contentType;
	/**
	 * 路径
	 */
	private String path;
	private String content;
	private Date uploadTime;
	


	public String getAttachId() {
		if(attachId == null){
			this.setAttachId(UUID.randomUUID().toString().replaceAll("-", ""));
		}
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
