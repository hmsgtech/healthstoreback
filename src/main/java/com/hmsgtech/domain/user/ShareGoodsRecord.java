package com.hmsgtech.domain.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * 分享记录，每日零点删除表
 *
 */
@Entity
@Table(indexes = { @Index(name = "ShareGoodsRecord_userid_index", columnList = "userid") })
public class ShareGoodsRecord {
	@Id
	@GeneratedValue
	private long id;

	@Column(columnDefinition = "VARCHAR(20) COMMENT '用户ID' ", nullable = false)
	private String userid;

	@Column(columnDefinition = "bigint(20) COMMENT '今日商品分享id' ", nullable = false)
	private long goodsId;

	@Temporal(TemporalType.DATE)
	private Date shareDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public Date getShareDate() {
		return shareDate;
	}

	public void setShareDate(Date shareDate) {
		this.shareDate = shareDate;
	}

	public long getGoodsId() {
		return goodsId;
	}

}
