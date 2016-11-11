package com.hmsgtech.domain.studioCustom;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hmsgtech.domain.studioArticle.ArticleUseRecord;

/**
 * 技师的顾客:扫二维码关注公众号获取
 * 
 * @author Vinney
 *
 */
@Entity
@Table(name = "studio_customer")
public class StudioCustomer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "techid", columnDefinition = "VARCHAR(64)  COMMENT '技师的unionid'")
	private String techid;

	@Column(name = "customer", columnDefinition = "VARCHAR(64)  COMMENT '客户openId'")
	private String customer;

	@Column(name = "nickname", columnDefinition = "varchar(32) COMMENT '微信昵称'")
	private String nickname;

	@Column(name = "name", columnDefinition = "varchar(20) COMMENT '客户名称:默认为空'")
	private String name;

	@Column(name = "headimgurl", columnDefinition = "varchar(200) COMMENT '微信头像'")
	private String headimgurl;

	@Column(name = "remark", columnDefinition = "varchar(255) COMMENT '备注:默认为空'")
	private String remark;

	@Column(name = "sex", columnDefinition = "int(1) COMMENT '性别1男2女'")
	private int sex;

	@Column(name = "birth", columnDefinition = "DATE COMMENT '生日'")
	private Date birth;

	@Column(name = "phone", columnDefinition = "varchar(20) COMMENT '手机'")
	private String phone;

	@Column(name = "profession", columnDefinition = "varchar(24) COMMENT '职业'")
	private String profession;

	@Column(name = "tags", columnDefinition = "varchar(64) COMMENT '标签数组'")
	private String tags;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP  COMMENT '创建时间'")
	private Date createTime = new Date();

	@Column(name = "update_time", columnDefinition = "DATETIME  COMMENT '更新时间：推送新消息的时间'")
	private Date updateTime;

	@Transient
	private String articleTitle;
	// 标签数组

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StudioCustomer(ArticleUseRecord articleUseRecord, WechatCustomer wxuser) {
		super();
		this.techid = articleUseRecord.getUnionid();
		this.customer = wxuser.getOpenid();
		this.nickname = wxuser.getNickname();
		this.headimgurl = wxuser.getHeadimgurl();
		this.sex = wxuser.getSex();
		this.createTime = new Date();
		this.updateTime = new Date();
		this.articleTitle = articleUseRecord.getArticleTitle();
	}

	public StudioCustomer(String techid, String customer, String nickname, String headimgurl, int sex, String title) {
		super();
		this.techid = techid;
		this.customer = customer;
		this.nickname = nickname;
		this.headimgurl = headimgurl;
		this.sex = sex;
		this.createTime = new Date();
		this.updateTime = new Date();
		this.articleTitle = title;
	}

	public StudioCustomer() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTechid() {
		return techid;
	}

	public void setTechid(String techid) {
		this.techid = techid;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

}
