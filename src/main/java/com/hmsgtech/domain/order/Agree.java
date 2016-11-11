package com.hmsgtech.domain.order;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 
 * 赞
 *
 */
@Entity
public class Agree {
	@Id
	@GeneratedValue
	private long id;
	
	private long rankId;
	
	private String userId;

	public long getRankId() {
		return rankId;
	}

	public void setRankId(long rankId) {
		this.rankId = rankId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
