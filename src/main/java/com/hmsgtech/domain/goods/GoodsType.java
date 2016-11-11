package com.hmsgtech.domain.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "goods_type")
public class GoodsType {
	@Id
	@GeneratedValue
	private int id;

	@Column(columnDefinition = "VARCHAR(16) COMMENT '名称' ")
	private String name;

	@JsonIgnore
	@Column(columnDefinition = "VARCHAR(255) COMMENT '名称' ")
	private String description;

	@Column(columnDefinition = "VARCHAR(255) COMMENT '图片' ")
	private String img;

	@JsonIgnore
	@Column(name = "orderby", columnDefinition = "int COMMENT '排序' ")
	private int sort;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
