package com.hmsgtech.domain.studioArticle;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "studio_article")
public class Article implements Serializable {
	private static final long serialVersionUID = -3420558365868464437L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "first_sort", columnDefinition = "int COMMENT '一级分类ID' ")
	private int firstSort;

	@Column(name = "second_sort", columnDefinition = "int COMMENT '二级分类ID' ")
	private int secondSort;

	@Column(name = "store_id", columnDefinition = "int COMMENT '机构ID' ")
	private int storeId;

	@Column(name = "publish_person", columnDefinition = "varchar(100) COMMENT '发表人id' ")
	private String studioUser;

	@Column(name = "title", columnDefinition = "varchar(100) COMMENT '标题'")
	private String title;

	@Column(name = "content", columnDefinition = "text COMMENT '内容'")
	private String content;

	@Column(name = "type", columnDefinition = "int COMMENT '类型 1:诊前须知，2：诊后须知'")
	private Integer type;

	@Column(name = "is_open", columnDefinition = "int default 1 COMMENT '是否公开 1:公开，2：只提供本机构查看' ")
	private Integer open;

	@Column(name = "use_count", columnDefinition = "int default 0 COMMENT '使用次数'")
	private int useCount;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP COMMENT '创建时间'")
	private Date createtime;
	@Transient
	private String agencyName;//
	@Transient
	private String techName;//
	@Transient
	private String techAvatar;//
	@Transient
	private String techDescription;//

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getTechName() {
		return techName;
	}

	public void setTechName(String techName) {
		this.techName = techName;
	}

	public String getTechAvatar() {
		return techAvatar;
	}

	public void setTechAvatar(String techAvatar) {
		this.techAvatar = techAvatar;
	}

	public String getTechDescription() {
		return techDescription;
	}

	public void setTechDescription(String techDescription) {
		this.techDescription = techDescription;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getFirstSort() {
		return firstSort;
	}

	public void setFirstSort(int firstSort) {
		this.firstSort = firstSort;
	}

	public int getSecondSort() {
		return secondSort;
	}

	public void setSecondSort(int secondSort) {
		this.secondSort = secondSort;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStudioUser() {
		return studioUser;
	}

	public void setStudioUser(String studioUser) {
		this.studioUser = studioUser;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
