package com.hmsgtech.domain.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * Created by 晓丰 on 2016/5/18.商户
 */
@Entity
public class Store {
	@Id
	@GeneratedValue
	private long id;

	private String name;

	private String address;

	private String phonenumber;

	private float latitude;

	private float longitude;

	private int rank;

	private int companyId;

	private String description;

	private String availableTime;

	private int cityId;

	private String img;

	@Column(name = "snapshot", columnDefinition = "VARCHAR(512) COMMENT '机构:营业执照照片'")
	private String snapshot;

	@Column(name = "manage", columnDefinition = "VARCHAR(64) COMMENT '机构管理员：默认为机构创建人ID'")
	private String manage;

	@Column(name = "industry", columnDefinition = "VARCHAR(64) COMMENT '机构服务分类：和文章一级分类是一套'")
	private String industry;

	@Column(name = "status", columnDefinition = "int(1) default -1 COMMENT '机构状态  0：正在审核 1：审核通过  2：审核未通过'")
	private Integer status;

	@Transient
	private String admin = "小伊";

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public String getManage() {
		return manage;
	}

	public void setManage(String manage) {
		this.manage = manage;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAvailableTime() {
		return availableTime;
	}

	public void setAvailableTime(String availableTime) {
		this.availableTime = availableTime;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
