package com.hmsgtech.domain.goods;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "classify_commodity")
public class ClassifyCommodity {
	@Id
	@GeneratedValue
	private int id;

	@Column(columnDefinition = "VARCHAR(16) COMMENT '分类名称' ", nullable = false)
	private String name;

	@Column(columnDefinition = "int(1) COMMENT '分类状态-状态 1：正常 0：冻结' ")
	private int status;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
