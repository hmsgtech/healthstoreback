package com.hmsgtech.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Reserve {
	@Id
	@GeneratedValue
	private int id;

	private String userid;

	@Column(columnDefinition = "VARCHAR(16) COMMENT '姓名'")
	private String name;
	@Column(columnDefinition = "VARCHAR(11) COMMENT '手机'")
	private String phone;
	@Column(columnDefinition = "VARCHAR(36) COMMENT '身份证'")
	private String cardNumber;
	@Column(columnDefinition = "int COMMENT '性别:1女2男'")
	private int sex;
	@Column(columnDefinition = "int COMMENT '已婚:0否1是'")
	private int marry;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getMarry() {
		return marry;
	}

	public void setMarry(int marry) {
		this.marry = marry;
	}

}
