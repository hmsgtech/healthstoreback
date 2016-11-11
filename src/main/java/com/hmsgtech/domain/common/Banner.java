package com.hmsgtech.domain.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 首页轮播图配置
 */
@Entity
@Table(name = "first_picture")
public class Banner {

	@JsonIgnore
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "img", columnDefinition = "VARCHAR(255) COMMENT '图片' ")
	private String img;

	@Column(name = "name", columnDefinition = "VARCHAR(16) COMMENT '名称' ")
	private String name;

	@Column(columnDefinition = "VARCHAR(16) COMMENT '名称' ")
	private String url;
	@JsonIgnore
	@Column(name = "orderby", columnDefinition = "int COMMENT '排序' ")
	private int sort;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
