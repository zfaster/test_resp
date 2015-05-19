package com.zzy.base.entity.chat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_chatfile")
public class ChatFile {
	public static final String BASEPATH = "D:/chatfile/";
	@Id
	@Column(name = "fileid")
	private String fileId;
	@Column(name = "name")
	private String name;
	@Column(name = "relativePath")
	private String relativePath;
	@Column(name = "contentType")
	private String contentType;
	@Column(name = "reciverId")
	private Integer reciverId;
	@Column(name = "createrId")
	private Integer createrId;
	@Column(name = "createTime")
	private Date createTime;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Integer getReciverId() {
		return reciverId;
	}

	public void setReciverId(Integer reciverId) {
		this.reciverId = reciverId;
	}

}
