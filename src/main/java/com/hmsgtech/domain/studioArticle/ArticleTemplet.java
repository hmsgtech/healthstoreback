package com.hmsgtech.domain.studioArticle;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_article_templet")
public class ArticleTemplet implements Serializable {
	private static final long serialVersionUID = -3420558365868464437L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "unionid", columnDefinition = "varchar(100) COMMENT '发表人id' ")
	private String unionid;

	@Column(name = "title", columnDefinition = "varchar(100) COMMENT '标题'")
	private String title;

	@Column(name = "content", columnDefinition = "VARCHAR(4000) COMMENT '内容'")
	private String content;

	@Column(name = "type", columnDefinition = "int COMMENT '类型 3:诊前须知，4：诊后须知'")
	private Integer type;

	@Column(name = "status", columnDefinition = "int COMMENT '状态 1:已发送，0：未发送'")
	private Integer status;

	@Column(name = "update_time", columnDefinition = "datetime COMMENT '更新时间'")
	private Date updatetime;

	public ArticleTemplet(String unionid, String title, String content, Integer type, Integer status) {
		super();
		this.unionid = unionid;
		this.title = title;
		this.content = content;
		this.type = type;
		this.status = status;
		this.updatetime = new Date();
	}

	public ArticleTemplet() {
		super();
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
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

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}
