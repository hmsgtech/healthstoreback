package com.hmsgtech.domain.studio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户加入机构申请记录
 * 
 * @author lujq
 *
 */
@Entity
@Table(name = "user_apply_record")
public class StudioApplyRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "unionid", columnDefinition = "VARCHAR(100)")
	private String unionid;

	@Column(name = "snapshot", columnDefinition = "varchar(200) COMMENT '员工头像'")
	private String snapshot;

	@Column(name = "name", columnDefinition = "varchar(30) COMMENT '员工名称'")
	private String name;

	@Column(name = "phone", columnDefinition = "varchar(20) COMMENT '员工电话'")
	private String phone;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP  COMMENT '创建时间'")
	private Date createTime = new Date();

	@Column(name = "status", columnDefinition = "int(1) default 0 COMMENT '状态：0拒绝1同意'")
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
