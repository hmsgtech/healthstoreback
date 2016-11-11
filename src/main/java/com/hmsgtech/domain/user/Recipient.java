package com.hmsgtech.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 收件人信息
 * 
 * @author lujq
 *
 */
@Entity
public class Recipient {
	@Id
	@GeneratedValue
	private long id;

	@Column(columnDefinition = "VARCHAR(12) COMMENT '联系人' ", nullable = false)
	private String name;

	@Column(columnDefinition = "VARCHAR(13) COMMENT '手机' ", nullable = false)
	private String phone;

	@Column(columnDefinition = "VARCHAR(64) COMMENT '地址' ", nullable = false)
	private String address;

	@Column(columnDefinition = "VARCHAR(10) COMMENT '邮政编码' ", nullable = false)
	private String postcode;

	@Column(columnDefinition = "VARCHAR(20) COMMENT '所属用户' ", nullable = false)
	private String userid;

	@Column(columnDefinition = "VARCHAR(64) COMMENT '备注' ", nullable = false)
	private String description;

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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

}
