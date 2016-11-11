package com.hmsgtech.domain.studioCustom;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 微信用户/:扫技师二维码关注公众号获取
 * 
 * @author Vinney
 *
 */
@Entity
@Table(name = "wechat_customer")
public class WechatCustomer {

	@Id
	@Column(name = "unionid", unique = true, columnDefinition = "VARCHAR(32)")
	private String unionid;

	@Column(name = "openid", columnDefinition = "varchar(32) COMMENT '客户ID'")
	private String openid;

	@Column(name = "nickname", columnDefinition = "varchar(32) COMMENT '昵称'")
	private String nickname;

	@Column(name = "headimgurl", columnDefinition = "varchar(256) COMMENT '头像'")
	private String headimgurl;

	@Column(name = "sex", columnDefinition = "int(1) COMMENT '性别1男2女'")
	private int sex;

	@Column(name = "province", columnDefinition = "varchar(20) COMMENT '省份'")
	private String province;

	@Column(name = "city", columnDefinition = "varchar(20) COMMENT '城市'")
	private String city;

	@Column(name = "scene", columnDefinition = "int COMMENT '首次扫码记录'")
	private int scene;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP  COMMENT '创建时间'")
	private Date createTime = new Date();

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
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

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int scene) {
		this.scene = scene;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
