package com.hmsgtech.domain.studioArticle;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 技师使用文章记录:诊前为二维码，诊后没有
 * 
 * @author lujq
 *
 */
@Entity
@Table(name = "article_use_record")
public class ArticleUseRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "int COMMENT '作为调取微信的场景ID' ")
	private int id;

	@Column(name = "unionid", columnDefinition = "VARCHAR(100)  COMMENT '技师ID'")
	private String unionid;

	@Column(name = "article_id", columnDefinition = "int COMMENT '文章ID或模板ID' ")
	private Integer articleId;

	@Column(name = "ticket", columnDefinition = "varchar(256) COMMENT '二维码ticket'")
	private String ticket;

	@Column(name = "ticket_url", columnDefinition = "varchar(512) COMMENT '二维码地址'")
	private String ticketUrl;

	@Column(name = "expire_time", columnDefinition = "datetime COMMENT '二维码失效时间'")
	private Date expireTime;

	@Column(name = "update_time", columnDefinition = "datetime COMMENT '上次刷新时间'")
	private Date updateTime;

	@Column(name = "article_title", columnDefinition = "VARCHAR(100)  COMMENT '文章标题'")
	private String articleTitle;

	@Column(name = "article_type", nullable = false, columnDefinition = "int(1) default 1 COMMENT '类型 1:诊前须知，2：诊后须知，3：技师模板，4：技师模板'")
	private int articleType;
	@Transient
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getArticleType() {
		return articleType;
	}

	public void setArticleType(int articleType) {
		this.articleType = articleType;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

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

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getTicketUrl() {
		return ticketUrl;
	}

	public void setTicketUrl(String ticketUrl) {
		this.ticketUrl = ticketUrl;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public ArticleUseRecord() {
		super();
	}

	public ArticleUseRecord(String unionid, int articleId, String articleTitle, int articleType) {
		super();
		this.unionid = unionid;
		this.articleId = articleId;
		this.articleTitle = articleTitle;
		this.articleType = articleType;
		this.updateTime = new Date();
	}

	public ArticleUseRecord(String unionid, String articleTitle, int articleType) {
		super();
		this.unionid = unionid;
		this.articleTitle = articleTitle;
		this.articleType = articleType;
		this.updateTime = new Date();
	}

}
