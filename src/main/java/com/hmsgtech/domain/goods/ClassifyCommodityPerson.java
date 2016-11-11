package com.hmsgtech.domain.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "classify_commodity_person")
public class ClassifyCommodityPerson {
	@Id
	@GeneratedValue
	private int id;

	@Column(name = "person_id", columnDefinition = "int COMMENT '人属性分类表id' ")
	private int personId;

	@Column(name = "commodity_id", columnDefinition = "int COMMENT '商品属性分类表id' ")
	private int commodityId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public int getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(int commodityId) {
		this.commodityId = commodityId;
	}

}
