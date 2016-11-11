package com.hmsgtech.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 用户状态
 * 
 * @author lujq
 *
 */
@Entity
public class UserStatus {
	@Id
	@Column(columnDefinition = "VARCHAR(20) COMMENT '用户ID' ", nullable = false)
	private String userid;

	@Column(columnDefinition = "int Default 0 COMMENT '抽奖次数,20求余'")
	private int luckDraw;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getLuckDraw() {
		return luckDraw;
	}

	public void setLuckDraw(int luckDraw) {
		this.luckDraw = luckDraw;
	}

}
