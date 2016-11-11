package com.hmsgtech.domain.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 商店图片：门脸，前台，治疗室或服务室，其他
 * 
 * @author lujq
 *
 */
@Entity
public class StoreImage {
	@Id
	@GeneratedValue
	private int id;

	@Column(name = "store_id", columnDefinition = "int COMMENT '店铺机构ID'")
	private int storeId;

	@Column(name = "img", columnDefinition = "VARCHAR(256) COMMENT '图片地址'")
	private String img;

	@Column(name = "type", columnDefinition = "int(1) default 9 COMMENT '图片类型:1门脸，2前台，3治疗室或服务室，9其他'")
	private Integer type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public StoreImage() {
		super();
	}

	public StoreImage(int storeId, Integer type) {
		super();
		this.img = "";
		this.storeId = storeId;
		this.type = type;
	}

}
