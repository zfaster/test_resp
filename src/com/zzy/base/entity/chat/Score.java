package com.zzy.base.entity.chat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="t_score")
public class Score {
	@Id
	@GeneratedValue(generator = "UUID")
	@Column(name="scoreid")
	private String scoreId;
	/**
	 * 被评分人
	 */
	@Column(name="scoreuser")
	private Integer scoreUser;
	/**
	 * 评分人
	 */
	@Column(name="scoreby")
	private Integer scoreBy;
	
	@Column(name="scoretime")
	private Date scoreTime;
	
	
	public Score() {
		super();
	}
	public Score(Integer scoreUser, Integer scoreBy) {
		super();
		this.scoreUser = scoreUser;
		this.scoreBy = scoreBy;
		this.scoreTime = new Date();
	}
	public String getScoreId() {
		return scoreId;
	}
	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}
	public Integer getScoreUser() {
		return scoreUser;
	}
	public void setScoreUser(Integer scoreUser) {
		this.scoreUser = scoreUser;
	}
	public Integer getScoreBy() {
		return scoreBy;
	}
	public void setScoreBy(Integer scoreBy) {
		this.scoreBy = scoreBy;
	}
	public Date getScoreTime() {
		return scoreTime;
	}
	public void setScoreTime(Date scoreTime) {
		this.scoreTime = scoreTime;
	}
}
