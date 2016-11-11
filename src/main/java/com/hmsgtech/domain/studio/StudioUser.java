package com.hmsgtech.domain.studio;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 用户
 * 
 * @author lujq
 *
 */
@Entity
@Table(name = "t_studio_user")
public class StudioUser {

	@Id
	@Column(name = "unionid", unique = true, columnDefinition = "VARCHAR(100)")
	private String unionid;

	@Column(name = "nickname", columnDefinition = "varchar(100)")
	private String nickname;

	@Column(name = "openId", columnDefinition = "varchar(100)")
	private String openId;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP  COMMENT '创建时间'")
	private Date createTime = new Date();

	@Column(name = "last_land_time", columnDefinition = "DATETIME  COMMENT '上次登陆时间'")
	private Date lastLandTime;

	@Column(name = "status", columnDefinition = "int(2) COMMENT '状态：0仅登陆，1完善资料，2选择机构-审核中，3审核通过，4审核失败-加入机构失败，5添加机构-审核中，6审核未通过-添加机构失败'")
	private Integer status;

	@Column(name = "identity", columnDefinition = "int(10) COMMENT '员工身份'")
	private Integer identity;// 身份id

	@Column(name = "name", columnDefinition = "varchar(30) COMMENT '员工名称'")
	private String name;// 名称

	@Column(name = "phone", columnDefinition = "varchar(20) COMMENT '员工电话'")
	private String phone;// 电话

	@Column(name = "snapshot", columnDefinition = "varchar(200) COMMENT '员工头像'")
	private String snapshot;

	@Column(name = "description", columnDefinition = "varchar(256) COMMENT '员工简介'")
	private String description;

	@Column(name = "agency_id", columnDefinition = "int(10) COMMENT '员工所属店铺/机构id'")
	private Integer agencyId;

	@Column(name = "reason", columnDefinition = "varchar(60) COMMENT '审核意见，不通过时设置'")
	private String reason;

	@Column(name = "working_years", columnDefinition = "int(2) COMMENT '工作年限'")
	private int workingYears;

	@Column(name = "specialty", columnDefinition = "varchar(100) COMMENT '技师专长:中文数组'")
	private String specialty;

	@Transient
	private String authority;

	@Transient
	private int isAdmin;
	@Transient
	private String agencyName;

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getLastLandTime() {
		return lastLandTime;
	}

	public void setLastLandTime(Date lastLandTime) {
		this.lastLandTime = lastLandTime;
	}

	public Integer getIdentity() {
		return identity;
	}

	public void setIdentity(Integer identity) {
		this.identity = identity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Integer agencyId) {
		this.agencyId = agencyId;
	}

	public int getWorkingYears() {
		return workingYears;
	}

	public void setWorkingYears(int workingYears) {
		this.workingYears = workingYears;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

}
