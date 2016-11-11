package com.hmsgtech.domain.studioCustom;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 客户标签
 * 
 * @author Vinney
 *
 */
@Entity
@Table(name = "custom_tag")
public class CustomTag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", columnDefinition = "varchar(12) COMMENT '标签' ")
	private String name;

	@Column(name = "unionid", columnDefinition = "varchar(30) COMMENT '添加人ID' ")
	private String unionid;

	@JsonIgnore
	@Column(name = "hot_sort", columnDefinition = "int DEFAULT 0 COMMENT '排序:根据热度排序，每次被使用+1'")
	private int hotSort;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP  COMMENT '创建时间'")
	private Date createTime;

	public CustomTag() {
		super();
	}

	public CustomTag(String name, String unionid) {
		super();
		this.name = name;
		this.unionid = unionid;
		this.hotSort = 0;
		this.createTime = new Date();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHotSort() {
		return hotSort;
	}

	public void setHotSort(int hotSort) {
		this.hotSort = hotSort;
	}

}
