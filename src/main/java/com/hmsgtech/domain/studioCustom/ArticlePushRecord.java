package com.hmsgtech.domain.studioCustom;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.hmsgtech.domain.studioArticle.ArticleUseRecord;

/**
 * 微信文章成功推送记录:扫码或发送记录
 * 
 * @author Vinney
 *
 */
@Entity
@Table(name = "article_push_record")
public class ArticlePushRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "studioCustomer", columnDefinition = "int COMMENT '客户编号：消息接收人'")
	private int studioCustomer;

	@Column(name = "status", columnDefinition = "int(1) default 1 COMMENT '状态:1sucess,2失败_取消关注, 9失败_未知原因'")
	private int status;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP  COMMENT '创建时间'")
	private Date createTime = new Date();

	@Column(name = "useRecord", columnDefinition = "int COMMENT '使用历史'")
	private int useRecord;

	@Transient
	private String title;

	@Transient
	private int type;

	public ArticlePushRecord(int studioCustomer, int useRecord, int articleType) {
		super();
		this.studioCustomer = studioCustomer;
		this.useRecord = useRecord;
		if (articleType > 2) {
			this.status = 0;
		} else {
			this.status = 1;
		}
		this.createTime = new Date();
	}

	public ArticlePushRecord() {
		super();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStudioCustomer() {
		return studioCustomer;
	}

	public void setStudioCustomer(int studioCustomer) {
		this.studioCustomer = studioCustomer;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getUseRecord() {
		return useRecord;
	}

	public void setUseRecord(int useRecord) {
		this.useRecord = useRecord;
	}

}
