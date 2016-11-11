package com.hmsgtech.domain.studioArticle;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "studio_second_sort")
public class ArticleSecondSort implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1027415005622550218L;

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "name", columnDefinition = "varchar(36) COMMENT '分类名称' ")
	private String name;

	@Column(name = "first_sort", columnDefinition = "int COMMENT '一级分类ID' ")
	private int firstSort;

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

	public int getFirstSort() {
		return firstSort;
	}

	public void setFirstSort(int firstSort) {
		this.firstSort = firstSort;
	}

}
