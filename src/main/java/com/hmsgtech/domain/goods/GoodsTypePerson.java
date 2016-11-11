package com.hmsgtech.domain.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "goods_type_person")
public class GoodsTypePerson {
	@Id
	@GeneratedValue
	private int id;

	@Column(name = "person_id", columnDefinition = "int COMMENT '人属性分类表id' ")
	private int personId;

	@Column(name = "type_id", columnDefinition = "int COMMENT '商品属性分类表id' ")
	private int typeId;

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

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

}
