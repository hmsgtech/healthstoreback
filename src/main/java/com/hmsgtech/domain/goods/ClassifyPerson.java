package com.hmsgtech.domain.goods;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "classify_person")
public class ClassifyPerson {
	@Id
	@GeneratedValue
	private int id;

	@Column(columnDefinition = "VARCHAR(16) COMMENT '分类名称' ", nullable = false)
	private String name;

	@Column(columnDefinition = "VARCHAR(64) COMMENT '分类描述' ")
	private String description;

	@Column(columnDefinition = "int(1) COMMENT '分类状态-状态 1：正常 0：冻结' ")
	private int status;

	@JsonIgnore
	@Column(columnDefinition = "int COMMENT '排序' ")
	private int orderby;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOrderby() {
		return orderby;
	}

	public void setOrderby(int orderby) {
		this.orderby = orderby;
	}

}
