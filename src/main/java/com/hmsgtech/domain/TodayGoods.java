package com.hmsgtech.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hmsgtech.domain.goods.Goods;

/**
 * 限时健康价商品
 * 
 * @author lujq
 *
 */
@Entity
@Table(indexes = { @Index(name = "order_by_index", columnList = "order_by") })
public class TodayGoods {
	@Id
	@GeneratedValue
	private long id;

	@Column(columnDefinition = "VARCHAR(12) COMMENT '图片小提示' ", nullable = false)
	private String title;

	@Column(columnDefinition = "VARCHAR(255) COMMENT '图片地址' ", nullable = false)
	private String image;

	@Column(columnDefinition = "int COMMENT '商品id' ", nullable = false)
	private int goodsId;

	@Column(name = "order_by", columnDefinition = "int Default 1 COMMENT '图片排序'")
	private int orderBy;

	@Transient
	private Goods goods;

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

}
