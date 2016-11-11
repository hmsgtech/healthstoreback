package com.hmsgtech.domain.studioArticle;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "studio_first_sort")
public class ArticleFirstSort implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6576982617545279042L;

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "name", columnDefinition = "varchar(36) COMMENT '分类名称' ")
	private String name;
	@JsonIgnore
	@Column(name = "img", columnDefinition = "varchar(256) COMMENT '分类图片' ")
	private String img;

	@Transient
	private List<ArticleSecondSort> children;

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

	public List<ArticleSecondSort> getChildren() {
		return children;
	}

	public void setChildren(List<ArticleSecondSort> children) {
		this.children = children;
	}

}
