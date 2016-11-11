package com.hmsgtech.domain.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 商品评分
 *
 */
@Entity
public class GoodsRank implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	private long orderId;

	// 商品评价
	private String gdescription;

	private long goodsId;

	private Date rankDate;

	private String userId;

	// 商品评分
	private int goodsRank;

	// 商品评价图片
	private String images;

	private long storeId;

	@Transient
	private boolean isAgree;

	@Transient
	private int agrees;
	// 商品评价图片
	@Transient
	private String nickName;

	@Transient
	private String snapshot;

	public GoodsRank() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getRankDate() {
		return rankDate;
	}

	public void setRankDate(Date rankDate) {
		this.rankDate = rankDate;
	}

	public long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public int getGoodsRank() {
		return goodsRank;
	}

	public void setGoodsRank(int goodsRank) {
		this.goodsRank = goodsRank;
	}

	public String getGdescription() {
		return gdescription;
	}

	public void setGdescription(String gdescription) {
		this.gdescription = gdescription;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public int getAgrees() {
		return agrees;
	}

	public void setAgrees(int agrees) {
		this.agrees = agrees;
	}

	public boolean isAgree() {
		return isAgree;
	}

	public void setAgree(boolean isAgree) {
		this.isAgree = isAgree;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

}
